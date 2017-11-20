package com.wind.adv;

import sharedadvertisement.wind.com.sharedadvertisement.R;
import utils.CommonUtil;
import utils.VideoInfo;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WaitUploadVideoActivity extends Activity {
	private EditText upload_video;
	private Button mDeleteVideo;
	private TextView mConfirm;
	private Bundle mBundle;
	private TextView mPlayCountTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_wait_upload_video);
		upload_video = (EditText) findViewById(R.id.upload_video);
		mDeleteVideo = (Button) findViewById(R.id.delete_video);
		mConfirm = (TextView) findViewById(R.id.confirm);
		mPlayCountTime = (TextView) findViewById(R.id.Play_countdown_text);
		
		Intent intent = getIntent();
		mBundle = intent.getExtras();
		
		mPlayCountTime.setText(mBundle.getString("countDownText"));
		
		initUploadVideoListener();
		initConfirmAndCancleListener();
		initDeleteVideoListener();
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
	
	private void chooseVideo() {
	   Intent intent = new Intent();
	   intent.setType("video/*");
	   intent.setAction(Intent.ACTION_GET_CONTENT);
	   startActivityForResult(intent, 1);
    } 
	
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
						int id = cursor2.getInt(cursor2.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
						String name = cursor2.getString(cursor2.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
						Log.i("minos", "id = " + id);
						setVideoPathAndId(path, id, name, "等待审核");
					}
					cursor2.moveToNext();
				}
				cursor2.close();

	    		upload_video.setText(path);
	    		
	    		Log.i("minos", "path = " + path);
	    	}
	    } 
	    super.onActivityResult(requestCode, resultCode, data);
	}

	private void setVideoPathAndId(String path, int id, String name, String status){
		VideoInfo vi = new VideoInfo(path, name, status);
		vi.setId(id);
		CommonUtil.storeVideoInfo(this, vi);
	}
	
	private void initConfirmAndCancleListener() {
		mConfirm.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
    		    intent.putExtras(mBundle);
    		    intent.setClass(WaitUploadVideoActivity.this, PlayCountDownActivity.class);
    		    startActivity(intent);
			}	
		});
	}
}
