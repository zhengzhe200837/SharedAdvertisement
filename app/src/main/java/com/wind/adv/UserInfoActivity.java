package com.wind.adv;

import sharedadvertisement.wind.com.sharedadvertisement.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserInfoActivity extends Activity {
    private TextView mTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_userinfo);
		RelativeLayout myOrderLayout = (RelativeLayout) findViewById(R.id.my_order);
		mTextView = (TextView) findViewById(R.id.cancle);
		
		myOrderLayout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent mIntent = new Intent(UserInfoActivity.this, MyOrderActivity.class);
				startActivity(mIntent);
			}
		});
		
		RelativeLayout myVideoLayout = (RelativeLayout) findViewById(R.id.my_video);
		
		myVideoLayout.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				Intent mIntent = new Intent(UserInfoActivity.this, MyVideoActivity.class);
				startActivity(mIntent);
			}
		});
		
		mTextView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				UserInfoActivity.this.finish();
			}
		});
		
	}

}
