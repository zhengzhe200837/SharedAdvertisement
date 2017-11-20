package com.wind.adv;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sharedadvertisement.wind.com.sharedadvertisement.R;
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
import android.text.InputType;
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
	private long playTime = 5;
	private int playTimes = 1;
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
		play_default_time.setInputType(InputType.TYPE_NULL);
		play_default_time.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View arg0, boolean hasFocus) {
				if(!hasFocus){
					calculateTotalPrice();
				}else {
					Intent intent = new Intent();
					intent.setClass(AdvancedOptionsActivity.this, SelectPlayTimeActivity.class);
					startActivityForResult(intent, 2);
				}
			}
		});
		
		play_default_time.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AdvancedOptionsActivity.this, SelectPlayTimeActivity.class);
				startActivityForResult(intent, 2);
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
		boolean isXiaomi = false;
	    if(requestCode == 1) {
	    	if(resultCode == RESULT_OK){
	    		Uri uri = data.getData();

				String path = "";
	    		if(!isXiaomi){
					Cursor cursor = getContentResolver().query(uri, null, null, null, null);
	    		    cursor.moveToFirst();
					path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
				}else {
					path = uri.getPath();
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
	    		
//	    		final MediaPlayer mediaPlayer = new MediaPlayer();
//	    		try {
//	    			if(isXiaomi){
//						mediaPlayer.setDataSource(path);
//					}else {
//						mediaPlayer.setDataSource(this, uri);
//					}
//
//					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//					mediaPlayer.prepare();
//				} catch (IllegalStateException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//					public void onPrepared(MediaPlayer mp) {
//						 int second = mp.getDuration()/1000;
//						 Log.i("minos", "mVideoDuration = " + mVideoDuration);
//						 if(second/60 > 0) {
//							 play_default_time.setText(second/60+"分" + second%60+"秒");
//							 playTime = second/60 + 1;
//						 }else {
//						 	 playTime = 1;
//							 play_default_time.setText(second + "秒");
//						 }
//					}
//				});

	    	}
	    }
	    
	    if(requestCode == 2){
	    	if(resultCode == RESULT_OK){
	    		playTime = data.getExtras().getInt("select_playtime");
	    		Log.i("minos", " playTime = " + playTime);

				play_default_time.setText(playTime + "分钟");
	    	}
	    }
	    
	    super.onActivityResult(requestCode, resultCode, data);
	}

	private void setVideoPathAndId(String path, int id, String name, String status){
		VideoInfo vi = new VideoInfo(path, name, status);
		vi.setId(id);
		CommonUtil.storeVideoInfo(this, vi);
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
					calculateTotalPrice();
					if(date > 0 || (date == 0 && playCountdown >= 5)){
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
	
	private void calculateCurrentTime(){
	    Calendar calendar = Calendar.getInstance();
		mCurrentMinute = calendar.get(Calendar.MINUTE);
	    mCurrentHour = calendar.get(Calendar.HOUR_OF_DAY);
	    mCurrentYear = calendar.get(Calendar.YEAR);
	    mCurrentMonth = calendar.get(Calendar.MONTH);
	    mCurrentDate = calendar.get(Calendar.DAY_OF_MONTH);
	}

}
