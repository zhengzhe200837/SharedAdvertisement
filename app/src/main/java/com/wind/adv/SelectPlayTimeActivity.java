package com.wind.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sharedadvertisement.wind.com.sharedadvertisement.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SelectPlayTimeActivity extends Activity{
    private List<Map<String, Object>> mList;
    private EditText mCustomEdit;
    private TextView mConfirm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_playtime);
		mCustomEdit = (EditText)findViewById(R.id.custom_edit);
		mConfirm = (TextView) findViewById(R.id.confirm);

		mList = new ArrayList<Map<String, Object>>();

		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.item_playtime_info,
				 new String[]{"playtime_info"},
				 new int[]{R.id.playtime_info});
		final ListView listView = (ListView) findViewById(R.id.playtime_listView);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int positon,
					long arg3) {
				Intent intent = new Intent();
				intent.putExtra("select_playtime", positon+1);

				SelectPlayTimeActivity.this.setResult(RESULT_OK, intent);
				SelectPlayTimeActivity.this.finish();

			}
		});

		mCustomEdit.setText("自定义");
		mCustomEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					int editNumber = Integer.parseInt(mCustomEdit.getText().toString());
					Intent intent = new Intent();
					intent.putExtra("select_playtime", editNumber);
					SelectPlayTimeActivity.this.setResult(RESULT_OK, intent);
					SelectPlayTimeActivity.this.finish();
				}
			}
		});

		mConfirm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				int editNumber = Integer.parseInt(mCustomEdit.getText().toString());
				Intent intent = new Intent();
				intent.putExtra("select_playtime", editNumber);
				SelectPlayTimeActivity.this.setResult(RESULT_OK, intent);
				SelectPlayTimeActivity.this.finish();
			}
		});
	}
	
	 private List<Map<String, Object>> getData() {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("playtime_info", "1分钟");
		 mList.add(map);
		 
		 map = new HashMap<String, Object>();
		 map.put("playtime_info", "2分钟");
		 mList.add(map);
		 
		 map = new HashMap<String, Object>();
		 map.put("playtime_info", "3分钟");
		 mList.add(map);
		 
		 map = new HashMap<String, Object>();
		 map.put("playtime_info", "4分钟");
		 mList.add(map);
		 
		 map = new HashMap<String, Object>();
		 map.put("playtime_info", "5分钟  默认");
		 mList.add(map);
		 
//		 map = new HashMap<String, Object>();
//		 map.put("playtime_info", "自定义");
//		 mList.add(map);
		 
		 return mList;
	 }
}
