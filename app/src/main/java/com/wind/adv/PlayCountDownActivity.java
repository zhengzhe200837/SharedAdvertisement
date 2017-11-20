package com.wind.adv;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import sharedadvertisement.wind.com.sharedadvertisement.MainActivity;
import sharedadvertisement.wind.com.sharedadvertisement.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class PlayCountDownActivity extends Activity {
    private TextView mPlayCountDown;
    private MyCountDownTimer  mTimer;
    private long millisInFuture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_play_counttime);
		
		mPlayCountDown = (TextView) findViewById(R.id.Play_countdown_text);
		TextView homePage = (TextView) findViewById(R.id.home_page);
		
		homePage.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				goToHomePageActivity();
			}
		});
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int selectMinute = bundle.getInt("selectMinute");
		int selectHour = bundle.getInt("selectHour");
		int selectYear = bundle.getInt("selectYear");
		int selectMonth = bundle.getInt("selectMonth");
		int selectDate = bundle.getInt("selectDate");
		
		Calendar calendar = Calendar.getInstance();
		int currentMinute = calendar.get(Calendar.MINUTE);
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
		
		String s1=selectYear + "-" + selectMonth + "-" + selectDate;
		String s2=currentYear + "-" + currentMonth + "-" + currentDate;
		int date = calculatePlayCountDown(s1, s2);
		
		millisInFuture = (date*24*60*60 +(selectHour - currentHour)*60*60 
				                        + (selectMinute - currentMinute)*60)*1000;
		startTimer();
	}

	private void startTimer() {
		if(mTimer == null){
			mTimer = new MyCountDownTimer(millisInFuture, 1000);
		}
		mTimer.start();
	}
	
	public class MyCountDownTimer extends CountDownTimer {

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);

		}

		@Override
		public void onFinish() {
			mPlayCountDown.setText("倒计时结束");
			goToHomePageActivity();
			if(mTimer != null){
				mTimer.cancel();
				mTimer = null;
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
			long time = millisUntilFinished/1000;
			
			if(time < 59){
				mPlayCountDown.setText(String.format("播放倒计时： 00:%02d", time));
			}else {
				if(time/60 <= 60){
					mPlayCountDown.setText(String.format("播放倒计时：%02d分钟  %02d秒", time/60, time%60));
				}else {
					if(time/60/60/24 > 0) {
						mPlayCountDown.setText(String.format("播放倒计时：%02d天 %02d小时 %02d分钟  %02d秒", 
								   time/60/60/24, time/60/60%24, (time/60- time/60/60/24*(24*60) - time/60/60%24*60), time%60));
					}else {
						mPlayCountDown.setText(String.format("播放倒计时：%02d小时 %02d分钟  %02d秒", 
	                               time/60/60, time/60%60, time%60));
					}
				}
			}
		}
		
	}
	
	private void goToHomePageActivity(){
		Intent intent = new Intent();
		intent.setClass(PlayCountDownActivity.this, MainActivity.class);
		startActivity(intent);
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
}
