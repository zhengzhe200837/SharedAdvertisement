package com.wind.adv;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import sharedadvertisement.wind.com.sharedadvertisement.R;
import utils.LogUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.calendar.utils.DateUtil;
import com.network.Network;
import com.network.model.SelectedPlayTimeSegment;
import com.network.model.UploadMyOrderInfo;

public class ConfirmPaymentActivity extends Activity {
	private TextView mStartTimeText;
	private TextView mPlayTimeText;
	private TextView mTotalPriceText;
	private TextView mCancleText;
	private TextView mPlayCountdownText;
	private View mCotentView;
	private PopupWindow mPopupWindow;
	private Button mConfirmPayMent;
	private Button mPayMentAtOnce;
	private Handler mHandler;
	private TextView mShowMessage;
	private Timer mTimer;
	private Bundle mBundle;
	private boolean isVideoUploaded;
	private String mTotalPriceString;
	private String mMySelectPlayStartTime;
	private ImageView mBillBoardPicture;
	private String mBillBoardPictureUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_confirm_payment);
		mStartTimeText = (TextView) findViewById(R.id.start_time_text);
		mPlayTimeText = (TextView) findViewById(R.id.play_time_text);
		mTotalPriceText = (TextView) findViewById(R.id.total_price_text);
		mCancleText = (TextView) findViewById(R.id.cancle_text);
		mPlayCountdownText = (TextView) findViewById(R.id.Play_countdown_text);
		mConfirmPayMent = (Button) findViewById(R.id.confirm_payment_text);
		mBillBoardPicture = (ImageView)findViewById(R.id.video_image);
		getBillBoardPictureUrl();
		if (mBillBoardPictureUrl != null) {
			Glide.with(this).load(mBillBoardPictureUrl).into(mBillBoardPicture);
		}

		Intent intent = getIntent();
		mMySelectPlayStartTime = intent.getStringExtra("selected_play_start_time");
		mUploadVideoPath = intent.getStringExtra("upload_video_path");
		mUploadVideoName = intent.getStringExtra("upload_video_name");
		String startTime = intent.getStringExtra("start_time");
		long durationTime = intent.getLongExtra("play_time", 0);
		int playTimes = intent.getIntExtra("play_times", 1);
		mTotalPriceString = intent.getStringExtra("total_price_text");
		long totalPrice = intent.getLongExtra("total_price", 10);
		int selectMinute = intent.getIntExtra("select_minute", 0);
		int selectHour = intent.getIntExtra("select_hour", 0);
		int selectYear = intent.getIntExtra("select_year", 0);
		int selectMonth = intent.getIntExtra("select_month", 0);
		int selectDate = intent.getIntExtra("select_date", 0);
		isVideoUploaded = intent.getBooleanExtra("video_is_upload", false);
		
		Calendar calendar = Calendar.getInstance();
		int currentMinute = calendar.get(Calendar.MINUTE);
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currentYear = DateUtil.getCurrentYear();
		int currentMonth = DateUtil.getCurrentMonth();
		int currentDate = DateUtil.getCurrentDay();
		
		mBundle = new Bundle();
		mBundle.putInt("selectMinute", selectMinute);
		mBundle.putInt("selectHour", selectHour);
		mBundle.putInt("selectYear", selectYear);
		mBundle.putInt("selectMonth", selectMonth);
		mBundle.putInt("selectDate", selectDate);
		
		String s1=selectYear + "-" + selectMonth + "-" + selectDate;
		String s2=currentYear + "-" + currentMonth + "-" + currentDate;
		android.util.Log.d("playtime", "s1 = " + s1);
		android.util.Log.d("playtime", "s2 = " + s2);
		int date = calculatePlayCountDown(s1, s2);
		android.util.Log.d("playtime", "date = " + date);
		if(date > 0) {
			mPlayCountdownText.setText(getString(R.string.Play_countdown) + date
                    + getString(R.string.date));
		}else {
			int playCountdown = selectHour*60 + selectMinute -(currentHour*60 + currentMinute);
			mPlayCountdownText.setText(getString(R.string.Play_countdown) + playCountdown
                    + getString(R.string.minute));
		}
		
		mBundle.putString("countDownText", mPlayCountdownText.getText().toString());
				
		mStartTimeText.setText(getString(R.string.start_time_segment) + ":" + startTime);
		mPlayTimeText.setText(getString(R.string.playback_length) + ":" + durationTime + "s" + "   "
		                          + getString(R.string.play_times) + ":"  + playTimes);
		mTotalPriceText.setText(mTotalPriceString);
		
		mCancleText.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				ConfirmPaymentActivity.this.finish();	
			}
		});
		
		initPopupWindow();
		
		mConfirmPayMent.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				onClickConfirmPayment();
			}
		});

		mUploadMyOrderInfo = new UploadMyOrderInfo.UploadMyOrderInfoBuilder()
				.setTableName("orderInfo")
				.setTodo("insert")
				.setBillBoardId(getSelectedBillBoardId())
				.setUserPhone(getUserPhone())
				.setPlayStartTime(mMySelectPlayStartTime)
				.setDurationTime((int)durationTime * playTimes)
				.setPlayTimes(playTimes)
				.setTotalPrice(totalPrice)
				.setBusinessPhone(getBusinessPhone())
				.setOrderStatus(2)             // 0审核通过还未播放，1已播放，2未审核，3审核不通过，4审核通过未上传
				.setMediaName(mUploadVideoName)
				.build();
		LogUtil.d("zz + ConfirmPaymentActivity + onCreate() + mUploadMyOrderInfo = " + mUploadMyOrderInfo.toString());
	}

	/**
	 * 获取广告牌图片url
	 */
	private void getBillBoardPictureUrl() {
		SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
		mBillBoardPictureUrl = sp.getString("billBoardPictureUrl", null);
	}

	/**
	 * 获取商户电话
	 */
	private String getBusinessPhone() {
		SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
		String businessPhone = sp.getString("businessPhone", "");
		return businessPhone;
	}

	/**
	 * 获取被选择的广告牌id
	 * @return
	 */
	private String getSelectedBillBoardId() {
		SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
		String selectedBillBoardId = sp.getString("selectedBillBoardId", "");
		return selectedBillBoardId;
	}

	/**
	 * 获取用户电话
	 * @return
	 */
	private String getUserPhone() {
		SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
		String phone = sp.getString("user_phone", "");
		return phone;
	}

	private void initPopupWindow() {
		mCotentView = LayoutInflater.from(this).inflate(R.layout.confirm_payment_popup, null, false);
		mPayMentAtOnce = (Button) mCotentView.findViewById(R.id.payment_at_once);
		TextView totalPrice = (TextView) mCotentView.findViewById(R.id.total_price);
		totalPrice.setText(mTotalPriceString);
		mPopupWindow = new PopupWindow(mCotentView, LayoutParams.MATCH_PARENT, 
				                         LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = (float) 1.0;
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getWindow().setAttributes(lp);
			}
		});
		
		mPayMentAtOnce.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				showCountdown();
			}
		});
	}

	private int calculatePlayCountDown(String selectDay, String currentDay){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		int date = 0;
		try {
			Date d1 = df.parse(selectDay);
			Date d2=df.parse(currentDay);
		    date = (int) ((d1.getTime()-d2.getTime())/(60*60*1000*24));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	private void onClickConfirmPayment(){
		mPopupWindow.showAtLocation(findViewById(R.id.Play_countdown_text), Gravity.BOTTOM, 0, 0);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = (float) 0.3;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		getWindow().setAttributes(lp);
	}
	
	private void showCountdown(){
		mPopupWindow.dismiss();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mShowMessage = new TextView(this);
	    mShowMessage.setText("等待返回付款结果: 5s");
	    final Dialog mDialog = new AlertDialog.Builder(this)
	                                    .setTitle("提示")  
                                        .setCancelable(false) 
	                                    .setView(mShowMessage)
	                                    .create();
	    mDialog.show();
	    
	    mHandler = new Handler(){
	    	public void handleMessage(Message msg){
	    		if(msg.what > 0) {
	    			mShowMessage.setText("等待返回付款结果: " + msg.what + "s");
	    		}else {
	    		    if(mDialog != null){
	    		    	mDialog.dismiss();
	    		    }
	    		    mTimer.cancel();

					uploadMyOrderInfo();
					uploadMyVideo();

	    		    if(isVideoUploaded){
	    		    	Intent intent = new Intent();
		    		    intent.putExtras(mBundle);
		    		    intent.setClass(ConfirmPaymentActivity.this, PlayCountDownActivity.class);
		    		    startActivity(intent);
	    		    }else {
	    		    	Intent intent = new Intent();
		    		    intent.putExtras(mBundle);
		    		    intent.setClass(ConfirmPaymentActivity.this, WaitUploadVideoActivity.class);
		    		    startActivity(intent);
	    		    }
	    		    
	    		    Toast.makeText(ConfirmPaymentActivity.this, "付款成功",  Toast.LENGTH_SHORT).show();
	    		    
	    		}
	    		 super.handleMessage(msg);
	    	}
	    };
	    
	    mTimer = new Timer(true);
	    TimerTask timeTask = new TimerTask() {
			int countTime = 5;
			public void run() {
				if(countTime > 0){
					countTime--;
				}
				
				Message msg = new Message(); 
				msg.what = countTime;
				mHandler.sendMessage(msg);
			}	
		};
		
		mTimer.schedule(timeTask, 1000, 1000);
	}

	private UploadMyOrderInfo mUploadMyOrderInfo;
	private String mUploadVideoPath;
	private String mUploadVideoName;
	private void uploadMyOrderInfo() {
		Network.uploadMyOrderInfo(mUploadMyOrderInfo);
	}
	private void uploadMyVideo() {
		if (mUploadVideoPath != null) {
			Network.uploadVideoFile(this, new File(mUploadVideoPath), getUserPhone() + "_" + mMySelectPlayStartTime + "_" + mUploadVideoName);
		}
	}
}
