package com.wind.adv;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sharedadvertisement.wind.com.sharedadvertisement.MainActivity;
import sharedadvertisement.wind.com.sharedadvertisement.R;
import sharedadvertisement.wind.com.sharedadvertisement.SelectAvailablePlayOutsetTimeActivity;
import utils.CommonUtil;
import utils.VideoInfo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.network.Network;

public class AdvancedOptionsActivity extends Activity {
	private EditText editStartTimeText;
	private EditText upload_video;
	private EditText play_default_time;
	private EditText play_default_times;
	private Button mDeleteVideo;
	private TextView mTotalPriceText;
	private long mTotalPrice;
	private TextView mconfirm;
	private TextView mCancle;
	private String mHour;
	private String mMinute;
	private String mDate;
	private String mShowDialogMessage;
	private long playTime = 0;
	private int playTimes = 1;
	private int mSelectSecond;
	private int mSelectMinute;
	private int mSelectHour;
	private int mSelectYear;
	private int mSelectMonth;
	private int mSelectDate;
	private long mVideoDuration;
	private int mCurrentYear;
	private int mCurrentMonth;
	private int mCurrentDate;
	private int mCurrentHour;
	private int mCurrentMinute;
	private TextView mChargeCriterion;
	private long mPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_advanced_options);
        mconfirm = (TextView) findViewById(R.id.confirm);
        mCancle = (TextView) findViewById(R.id.cancle);
        editStartTimeText = (EditText) findViewById(R.id.edit_startTime);
        upload_video = (EditText) findViewById(R.id.upload_video);
        play_default_time = (EditText) findViewById(R.id.play_default_time);
        play_default_times = (EditText) findViewById(R.id.play_default_times);
		play_default_times.setInputType(InputType.TYPE_CLASS_NUMBER);  //调用数字键盘，保证edittext只能输入数字
        mTotalPriceText = (TextView) findViewById(R.id.total_price);
        mDeleteVideo = (Button) findViewById(R.id.delete_video);
		mChargeCriterion = (TextView)findViewById(R.id.minute_price);
		mPrice = getIntent().getLongExtra(MainActivity.CHARGECRITERION, 0);
		mChargeCriterion.setText(String.valueOf(mPrice) + "元/秒");
        
        initUploadVideoListener();
        initEditStartTimeTextListener();
        initPlayTimeListener();
        initPlayTimesListener();
        initConfirmAndCancleListener();
        initDeleteVideoListener();
	}

	private void initConfirmAndCancleListener() {
		mconfirm.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				showDialog();
			}	
		});
		
		mCancle.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				AdvancedOptionsActivity.this.finish();				
			}
		});
	}
	
	private void initDeleteVideoListener() {
		mDeleteVideo.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				upload_video.setText("");
			}
		});
	}

	private void initUploadVideoListener() {
		upload_video.setInputType(InputType.TYPE_NULL);
		upload_video.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View arg0, boolean hasFocus) {
				if(hasFocus) {
					chooseVideo();
				}
			}
		});
		
		upload_video.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				chooseVideo();
			}
		});	
	}
	
	private void initEditStartTimeTextListener() {
		calculateCurrentTime();
		String sCurrentHour = "";
		String sCurrentMinute = "";
		if(mCurrentHour<10){
             sCurrentHour = "0" + mCurrentHour;
        }else {
        	 sCurrentHour = String.valueOf(mCurrentHour);
        }
		 
        if(mCurrentMinute<10){
            sCurrentMinute = "0" + mCurrentMinute;
        }else {
        	sCurrentMinute = String.valueOf(mCurrentMinute);
        }
        
		editStartTimeText.setHint(mCurrentYear + "-" + (mCurrentMonth+1) + "-" + mCurrentDate + " "
				                    + sCurrentHour + ":" + sCurrentMinute); 
		editStartTimeText.setInputType(InputType.TYPE_NULL);

//		editStartTimeText.setOnFocusChangeListener(new OnFocusChangeListener() {
//			public void onFocusChange(View arg0, boolean hasFocus) {
//				if(hasFocus){
//					showDataPickerDialog(); //使用系统选择时间方法
//				}
//			}
//		});
		
		editStartTimeText.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
//				showDataPickerDialog();  //使用系统选择时间方法
				Intent intent = new Intent(AdvancedOptionsActivity.this, SelectAvailablePlayOutsetTimeActivity.class);
				startActivityForResult(intent, 3);
			}
		});
	}
	
	private void initPlayTimesListener() {
		play_default_times.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				calculateTotalPrice();
			}
		});
	}

	private void initPlayTimeListener() {
		play_default_time.setEnabled(false);
		play_default_time.setInputType(InputType.TYPE_NULL);
		play_default_time.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				calculateTotalPrice();
			}
		});
		
		play_default_time.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//进入选择播放时长activity  start
//				Intent intent = new Intent();
//				intent.setClass(AdvancedOptionsActivity.this, SelectPlayTimeActivity.class);
//				startActivityForResult(intent, 2);
				//进入选择播放时长activity   end
			}
		});
		
	}
	
	private void calculateTotalPrice(){
		String playDefaultTimes = play_default_times.getText().toString();
		String playDefaultTime = play_default_time.getText().toString();
		
		if(playDefaultTimes.equals("")){
			playTimes = 1;
		}else {
			playTimes = Integer.parseInt(playDefaultTimes);
		}
		
		if(playDefaultTime.equals("")){
			playTime = 0;
		}
		if (playDefaultTime.contains("分钟")) {
			playTime = Integer.parseInt(playDefaultTime.substring(0, playDefaultTime.indexOf("分钟"))) * 60;
		} else if (playDefaultTime.contains("秒")){
			playTime = Integer.parseInt(playDefaultTime.substring(0, playDefaultTime.indexOf("秒")));
		}

		mTotalPrice = playTime*playTimes*mPrice;
		mTotalPriceText.setText("总价：" + mTotalPrice + "元");
	}
	
	private void showDataPickerDialog() {
		Calendar calendar = Calendar.getInstance();
		
		new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				mSelectYear = year;
				mSelectMonth = monthOfYear;
				mSelectDate = dayOfMonth;
				mDate = year + "/" + (monthOfYear+1) + "/" +  dayOfMonth;
				showTimePickerDialog();
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();	
	}
	
	private void showTimePickerDialog() {
		calculateCurrentTime();
		TimePickerDialog timeDialog = new TimePickerDialog(this, new OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				 mSelectHour = hourOfDay;
				 mSelectMinute = minute;
				 if(hourOfDay<10){
                     mHour="0"+hourOfDay;
                 }else {
                	 mHour = String.valueOf(hourOfDay);
                 }
				 
                 if(minute<10){
                     mMinute="0"+minute;
                 }else {
                	 mMinute = String.valueOf(minute);
                 }
                 
                 editStartTimeText.setText(mDate + "  " + mHour + ":" + mMinute);
			}
		}, mCurrentHour, mCurrentMinute, true);
		timeDialog.show();
	}
	

	private void chooseVideo() {
       Intent intent = new Intent();
       intent.setType("video/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
//		intent.setAction(Intent.ACTION_PICK);
       startActivityForResult(intent, 1);
	} 
	
	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(requestCode == 1) {    //获取视频路径
	    	if(resultCode == RESULT_OK){
				Uri uri = data.getData();
				String path = "";

				final String scheme = uri.getScheme();

				if(scheme == null) {
					path = uri.getPath();
				}else if(ContentResolver.SCHEME_FILE.equals(scheme)){
					path = uri.getPath();
				}else if(ContentResolver.SCHEME_CONTENT.equals(scheme)){
					Cursor cursor = getContentResolver().query(uri, null, null, null, null);
					if(null != cursor){
						cursor.moveToFirst();
						path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
						cursor.close();
					}
				}

				String[] projection = new String[] {
						MediaStore.Video.Media.DATA,
						MediaStore.Video.Media._ID,
						MediaStore.Video.Media.DURATION,
						MediaStore.Video.Media.DISPLAY_NAME
				};

				Cursor cursor2 = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
						null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
				cursor2.moveToFirst();
				int fileNum = cursor2.getCount();

				for(int counter = 0; counter < fileNum; counter++){
					if(path.equals(cursor2.getString(cursor2.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)))){
						long duration = cursor2.getLong(cursor2.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
						int id = cursor2.getInt(cursor2.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
						String name = cursor2.getString(cursor2.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
						Log.i("minos",  " duration = " + duration + " id = " + id);

						long second = duration/1000;
						if(second/60 > 0) {
							 play_default_time.setText(second/60+"分" + second%60+"秒");
							 playTime = second/60 + 1;
						}else {
						 	 playTime = 1;
							 play_default_time.setText(second + "秒");
						}

						setVideoPathAndId(path, id, name, "等待审核");
					}
					cursor2.moveToNext();
				}
				cursor2.close();

				Log.i("minos", " path = " + path);
	    		upload_video.setText(path);

	    	}
	    }
	    
	    if(requestCode == 2){   //选择播放时长activity SelectPlayTimeActivity
	    	if(resultCode == RESULT_OK){
	    		playTime = data.getExtras().getInt("select_playtime");
	    		Log.i("minos", " playTime = " + playTime);

				play_default_time.setText(playTime + "分钟");
	    	}
	    }

	    if (requestCode == 3) {    // SelectAvailablePlayOutsetTimeActivity
			if (resultCode == RESULT_OK) {
				mMySelectPlayStartTime = data.getStringExtra("selected_play_start_time");
				mSelectYear = Integer.parseInt(mMySelectPlayStartTime.substring(0, 4));
				mSelectMonth = Integer.parseInt(mMySelectPlayStartTime.substring(4, 6));
				mSelectDate = Integer.parseInt(mMySelectPlayStartTime.substring(6, 8));
				mSelectHour = Integer.parseInt(mMySelectPlayStartTime.substring(8, 10));
				mSelectMinute = Integer.parseInt(mMySelectPlayStartTime.substring(10, 12));
				mSelectSecond = Integer.parseInt(mMySelectPlayStartTime.substring(12));

				editStartTimeText.setText(mMySelectPlayStartTime.substring(0, 4) + "-"
						+ mMySelectPlayStartTime.substring(4, 6) + "-"
						+ mMySelectPlayStartTime.substring(6, 8) + " "
						+ mMySelectPlayStartTime.substring(8, 10) + ":"
						+  mMySelectPlayStartTime.substring(10, 12) + ":"
						+ mMySelectPlayStartTime.substring(12));
			}
		}
	    
	    super.onActivityResult(requestCode, resultCode, data);
	}

	private String mMySelectPlayStartTime;
	private String mUploadVideoPath;
	private String mUploadVideoName;

	private void setVideoPathAndId(String path, int id, String name, String status){
		mUploadVideoName = name;
		mUploadVideoPath = path;
	}

	private void showDialog() {
		final AlertDialog.Builder Dialog = 
	            new AlertDialog.Builder(this);
		Log.i("minos", "minos upload_video.getText().tostring = " + upload_video.getText().toString());
		if(upload_video.getText().toString().equals("") || upload_video.getText() == null){
			mShowDialogMessage = getString(R.string.no_video_message);
		}else {
			mShowDialogMessage = getString(R.string.pay_message);
		}
		
		calculateCurrentTime();
		
		String s1=mSelectYear + "-" + mSelectMonth + "-" + mSelectDate;
		android.util.Log.d("zz", "data1 = " + s1);
		android.util.Log.d("zz", "data2 = " + mSelectHour + " " + mSelectMinute);
		String s2=mCurrentYear + "-" + mCurrentMonth + "-" + mCurrentDate;
		final int date = calculatePlayCountDownD(s1, s2);
		final int playCountdown = mSelectHour*60 + mSelectMinute -(mCurrentHour*60 + mCurrentMinute);
		
		if(date < 0){
			mShowDialogMessage = getString(R.string.select_time_erro);
		}else if(date == 0) {
			if(playCountdown < 5) {
				mShowDialogMessage = getString(R.string.select_time_erro);
			}
		}
		
		Dialog.setMessage(mShowDialogMessage)
		      .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					if(date > 0 || (date == 0 && playCountdown >= 5)){
						Intent intent = new Intent();
						intent.putExtra("selected_play_start_time", mMySelectPlayStartTime);
						intent.putExtra("start_time", editStartTimeText.getText().toString());
						intent.putExtra("play_time", playTime);
						intent.putExtra("play_times", playTimes);
						intent.putExtra("select_minute", mSelectMinute);
						intent.putExtra("select_hour", mSelectHour);
						intent.putExtra("select_year", mSelectYear);
						intent.putExtra("select_month", mSelectMonth);
						intent.putExtra("select_date", mSelectDate);
						intent.putExtra("total_price_text", mTotalPriceText.getText().toString());
						intent.putExtra("total_price", mTotalPrice);
						intent.putExtra("upload_video_path", mUploadVideoPath);
						intent.putExtra("upload_video_name", mUploadVideoName);

						if(upload_video.getText().toString().equals("") || upload_video.getText() == null){
							intent.putExtra("video_is_upload", false);
						}else {
							intent.putExtra("video_is_upload", true);
						}
						intent.setClass(AdvancedOptionsActivity.this, ConfirmPaymentActivity.class);
						startActivity(intent);
					}
				}
			  })
			  .setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			  })
			  .show();		
	}
	
	private int calculatePlayCountDownD(String selectDay, String currentDay){
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
	
	private void calculateCurrentTime(){
	    Calendar calendar = Calendar.getInstance();
		mCurrentMinute = calendar.get(Calendar.MINUTE);
	    mCurrentHour = calendar.get(Calendar.HOUR_OF_DAY);
	    mCurrentYear = calendar.get(Calendar.YEAR);
	    mCurrentMonth = calendar.get(Calendar.MONTH);
	    mCurrentDate = calendar.get(Calendar.DAY_OF_MONTH);
	}

}
