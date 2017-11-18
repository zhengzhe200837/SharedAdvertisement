package com.wind.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sharedadvertisement.wind.com.sharedadvertisement.R;
import utils.CommonUtil;
import utils.VideoInfo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyOrderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.item_myorder_info,
				 new String[]{"video_image", "video_title", "video_status"},
				 new int[]{R.id.video_image, R.id.video_title, R.id.video_status});
		ListView listview = findViewById(R.id.myOrder_listView);
		listview.setAdapter(adapter);
	}
	
	 private List<Map<String, Object>> getData() {
		 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		 for (int i = 1; i <= CommonUtil.ORDER_TOTAL_SIZE; i++) {
			 VideoInfo vi = CommonUtil.getVideoInfo(MyOrderActivity.this, i);
			 Map<String, Object> map = new HashMap<String, Object>();
			 android.util.Log.d("zz", "path = " + vi.getPath());
			 android.util.Log.d("zz", "bitmap = " + retriveVideoFrameFromVideo(vi.getPath()));
			 map.put("video_image", retriveVideoFrameFromVideo(vi.getPath()));
			 map.put("video_title", vi.getName());
			 map.put("video_status", vi.getStatus());
			 list.add(map);
		 }
		 return list;
	 }

	 private Bitmap getFirstFrame(String path) {
		 MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		 try {
			 retriever.setDataSource(path);
			 Bitmap frameAtTime = retriever.getFrameAtTime();
			 return frameAtTime;
		 } catch (Exception e) {
			 return null;
		 } finally {
			 retriever.release();
		 }
	 }

	public Bitmap retriveVideoFrameFromVideo(String videoPath)
	{
		Bitmap bitmap = null;
		MediaMetadataRetriever mediaMetadataRetriever = null;
		try
		{
			mediaMetadataRetriever = new MediaMetadataRetriever();
			if (Build.VERSION.SDK_INT >= 14)
				mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
			else
				mediaMetadataRetriever.setDataSource(videoPath);
			bitmap = mediaMetadataRetriever.getFrameAtTime();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (mediaMetadataRetriever != null)
			{
				mediaMetadataRetriever.release();
			}
		}
		return bitmap;
	}
}
