package com.wind.adv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sharedadvertisement.wind.com.sharedadvertisement.R;

public class MyVideoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myvideo);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.item_myorder_info,
				 new String[]{"video_image", "video_title", "video_status"},
				 new int[]{R.id.video_image, R.id.video_title, R.id.video_status});
		ListView listview = findViewById(R.id.myVideo_listView);
		listview.setAdapter(adapter);
	}
	
	 private List<Map<String, Object>> getData() {
		 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		 
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("video_image", R.drawable.billboard_photos);
		 map.put("video_title", getString(R.string.user_rename));
		 map.put("video_status", getString(R.string.order_info1));
		 list.add(map);
		 
		 map = new HashMap<String, Object>();
		 map.put("video_image", R.drawable.billboard_photos);
		 map.put("video_title", getString(R.string.auto_name));
		 map.put("video_status", getString(R.string.order_info2));
		 list.add(map);
		 
		 map = new HashMap<String, Object>();
		 map.put("video_image", R.drawable.billboard_photos);
		 map.put("video_title", getString(R.string.billboard_name));
		 map.put("video_status", getString(R.string.order_info2));
		 list.add(map);
		 
		 return list;
	 }
}
