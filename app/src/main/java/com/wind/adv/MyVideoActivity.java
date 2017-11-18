package com.wind.adv;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sharedadvertisement.wind.com.sharedadvertisement.R;

public class MyVideoActivity extends Activity {
	private ContentResolver mContentResolver;
	private List<Video> mVidoList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myvideo);
		mContentResolver = getContentResolver();
		mVidoList = getVideos();
		android.util.Log.d("zz", "getExternalStorageDirectory() = " + Environment.getExternalStorageDirectory());

		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.item_myorder_info,
				 new String[]{"video_image", "video_title", "video_status"},
				 new int[]{R.id.video_image, R.id.video_title, R.id.video_status});
		ListView listview = findViewById(R.id.myVideo_listView);
		listview.setAdapter(adapter);
	}
	
	 private List<Map<String, Object>> getData() {
		 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		 for(int i = 0; i < mVidoList.size(); i++) {
		 	 Video v = mVidoList.get(i);
			 Map<String, Object> map = new HashMap<String, Object>();
			 map.put("video_image", R.drawable.billboard_photos);
			 map.put("video_title", v.path);
			 map.put("video_status", v.name);
			 list.add(map);
		 }
		 
//		 Map<String, Object> map = new HashMap<String, Object>();
//		 map.put("video_image", R.drawable.billboard_photos);
//		 map.put("video_title", getString(R.string.user_rename));
//		 map.put("video_status", getString(R.string.order_info1));
//		 list.add(map);
//
//		 map = new HashMap<String, Object>();
//		 map.put("video_image", R.drawable.billboard_photos);
//		 map.put("video_title", getString(R.string.auto_name));
//		 map.put("video_status", getString(R.string.order_info2));
//		 list.add(map);
//
//		 map = new HashMap<String, Object>();
//		 map.put("video_image", R.drawable.billboard_photos);
//		 map.put("video_title", getString(R.string.billboard_name));
//		 map.put("video_status", getString(R.string.order_info2));
//		 list.add(map);
		 
		 return list;
	 }

	/**
	 * 获取本机视频列表
	 * @return
	 */
	private List<Video> getVideos() {

		List<Video> videos = new ArrayList<Video>();

		Cursor c = null;
		Uri uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
		try {
			c = getContentResolver().query(uri, null, null, null,
					MediaStore.Video.Media.DEFAULT_SORT_ORDER);
			android.util.Log.d("zz", "count = " + c.getCount());
			while (c.moveToNext()) {
				String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
				android.util.Log.d("zz", "path = " + path);
				int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
				String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
				String resolution = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)); //分辨率
				long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));// 大小
				long duration = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
				long date = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));//修改时间

				Video video = new Video(id, path, name, resolution, size, date, duration);
				videos.add(video);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return videos;
	}

	public static class Video {
		private int id = 0;
		private String path = null;
		private String name = null;
		private String resolution = null;// 分辨率
		private long size = 0;
		private long date = 0;
		private long duration = 0;

		public Video(int id, String path, String name, String resolution, long size, long date, long duration) {
			this.id = id;
			this.path = path;
			this.name = name;
			this.resolution = resolution;
			this.size = size;
			this.date = date;
			this.duration = duration;
		}
	}
}
