package com.wind.adv;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import sharedadvertisement.wind.com.sharedadvertisement.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ConfirmPaymentActivity extends Activity {
	private TextView mStartTimeText;
	private TextView mPlayTimeText;
	private TextView mTotalPriceText;
	private TextView mCancleText;
	private TextView mPlayCountdownText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_confirm_payment);
		mStartTimeText = (TextView) findViewById(R.id.start_time_text);
		mPlayTimeText = (TextView) findViewById(R.id.play_time_text);
		mTotalPriceText = (TextView) findViewById(R.id.total_price_text);
		mCancleText = (TextView) findViewById(R.id.cancle_text);
		mPlayCountdownText = (TextView) findViewById(R.id.Play_countdown_text);
		
		Intent intent = getIntent();
		String startTime = intent.getStringExtra("start_time");
		int playTime = intent.getIntExtra("play_time", 5);
		int playTimes = intent.getIntExtra("play_times", 1);
		String totalPrice = intent.getStringExtra("total_price");
		int selectMinute = intent.getIntExtra("select_minute", 0);
		int selectHour = intent.getIntExtra("select_hour", 0);
		int selectYear = intent.getIntExtra("select_year", 0);
		int selectMonth = intent.getIntExtra("select_month", 0);
		int selectDate = intent.getIntExtra("select_date", 0);
		
		Calendar calendar = Calendar.getInstance();
		int currentMinute = calendar.get(Calendar.MINUTE);
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
		
		String s1=selectYear + "-" + selectMonth + "-" + selectDate;
		String s2=currentYear + "-" + currentMonth + "-" + currentDate;
		int date = calculatePlayCountDownD(s1, s2);	

		if(date > 0) {
			mPlayCountdownText.setText(getString(R.string.Play_countdown) + date
                    + getString(R.string.date));
		}else {
			int playCountdown = selectHour*60 + selectMinute -(currentHour*60 + currentMinute);
			mPlayCountdownText.setText(getString(R.string.Play_countdown) + playCountdown
                    + getString(R.string.minute));
		}
				
		mStartTimeText.setText(getString(R.string.start_time) + ":" + startTime);
		mPlayTimeText.setText(getString(R.string.playback_length) + ":" + playTime + "   " 
		                          + getString(R.string.play_times) + ":"  + playTimes);
		mTotalPriceText.setText(totalPrice);
		
		mCancleText.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				ConfirmPaymentActivity.this.finish();	
			}
		});
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
