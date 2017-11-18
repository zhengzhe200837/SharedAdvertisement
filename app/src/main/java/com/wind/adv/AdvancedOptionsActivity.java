package com.wind.adv;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sharedadvertisement.wind.com.sharedadvertisement.R;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class AdvancedOptionsActivity extends Activity {
	private EditText editStartTimeText;
	private EditText upload_video;
	private EditText play_default_time;
	private EditText play_default_times;
	private Button mDeleteVideo;
	private TextView mTotalPrice;
	private TextView mconfirm;
	private TextView mCancle;
	private String mHour;
	private String mMinute;
	private String mDate;
	private String mShowDialogMessage;
	private int playTime = 5;
	private int playTimes = 1;
	private int mSelectMinute;
	private int mSelectHour;
	private int mSelectYear;
	private int mSelectMonth;
	private int mSelectDate;

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
        mTotalPrice = (TextView) findViewById(R.id.total_price);
        mDeleteVideo = (Button) findViewById(R.id.delete_video);
        
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
		editStartTimeText.setInputType(InputType.TYPE_NULL);
		editStartTimeText.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View arg0, boolean hasFocus) {
				if(hasFocus){
					showDataPickerDialog();
				}		
			}		
		});
		
		editStartTimeText.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				showDataPickerDialog();
			}
		});
	}
	
	private void initPlayTimesListener() {
		play_default_times.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View arg0, boolean hasFocus) {
				if(!hasFocus){
					calculateTotalPrice();
				}
			}
		});
	}

	private void initPlayTimeListener() {
		play_default_time.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View arg0, boolean hasFocus) {
				if(!hasFocus){
					calculateTotalPrice();
				}
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
			playTime = 5;
		}else {
			playTime = Integer.parseInt(playDefaultTime);
		}
		
		mTotalPrice.setText("总价：" + playTime*playTimes*10 + "元");
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
		}, 0, 0, true);
		timeDialog.show();
	}
	

	private void chooseVideo() {
       Intent intent = new Intent();
       intent.setType("video/*");
		intent.setType("video/*;image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(intent, 1);
	} 
	
	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    if(requestCode == 1) {
	    	if(resultCode == RESULT_OK){
	    		Uri uri = data.getData();
	    		Log.i("minos","uri.toString = " + toString() + "  uri.getPath() = " + uri.getPath());

				Cursor cursor = getContentResolver().query(uri, null, null, null, null);
				cursor.moveToFirst();
				int idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);

	    		upload_video.setText(cursor.getString(idx));
	    	}
	    }
	    super.onActivityResult(requestCode, resultCode, data);
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
		
		Calendar calendar = Calendar.getInstance();
		int currentMinute = calendar.get(Calendar.MINUTE);
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
		
		String s1=mSelectYear + "-" + mSelectMonth + "-" + mSelectDate;
		String s2=currentYear + "-" + currentMonth + "-" + currentDate;
		final int date = calculatePlayCountDownD(s1, s2);
		final int playCountdown = mSelectHour*60 + mSelectMinute -(currentHour*60 + currentMinute);
		
		if(date < 0){
			mShowDialogMessage = getString(R.string.select_time_erro);
		}else if(date == 0) {
			if(playCountdown < 30) {
				mShowDialogMessage = getString(R.string.select_time_erro);
			}
		}
		
		Dialog.setMessage(mShowDialogMessage)
		      .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					calculateTotalPrice();
					if((date > 0 || (date == 0 && playCountdown >= 30))
						 && (!upload_video.getText().toString().equals("") 
						 || upload_video.getText() == null)){
						Intent intent = new Intent();
						intent.putExtra("start_time", editStartTimeText.getText().toString());
						intent.putExtra("play_time", playTime);
						intent.putExtra("play_times", playTimes);
						intent.putExtra("select_minute", mSelectMinute);
						intent.putExtra("select_hour", mSelectHour);
						intent.putExtra("select_year", mSelectYear);
						intent.putExtra("select_month", mSelectMonth);
						intent.putExtra("select_date", mSelectDate);
						intent.putExtra("total_price", mTotalPrice.getText().toString());
						intent.setClass(AdvancedOptionsActivity.this, ConfirmPaymentActivity.class);
						startActivity(intent);
					}
				}
			  })
			  .setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					calculateTotalPrice(); 
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

}
