package com.wind.adv;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sharedadvertisement.wind.com.sharedadvertisement.AdvertisementBoardDetailInfoActivity;
import sharedadvertisement.wind.com.sharedadvertisement.DisplayVideoActivity;
import sharedadvertisement.wind.com.sharedadvertisement.R;

public class MyVideoActivity extends Activity {
	private ContentResolver mContentResolver;
	private List<Video> mVidoList = null;
	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myvideo);
		mContentResolver = getContentResolver();
		mVidoList = getVideos();
		mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		RecyclerViewAdapter rva = new RecyclerViewAdapter(this);
		rva.setVideoList(mVidoList);
		rva.setContentResolver(mContentResolver);
		mRecyclerView.setAdapter(rva);
	}

	public static class RecyclerViewAdapter extends RecyclerView.Adapter<MyVideoActivity.RecyclerViewAdapter.ItemViewHolder> {
		private Context mContext;
		private List<Video> mVideoList;
		private ContentResolver mContentResolver;

		public RecyclerViewAdapter(Context context) {
			mContext = context;
		}
		public void setVideoList(List<Video> list) {
			mVideoList = list;
		}
		public void setContentResolver(ContentResolver cr) {
			mContentResolver = cr;
		}

		@Override
		public int getItemCount() {
			return mVideoList.size();
		}

		@Override
		public void onBindViewHolder(ItemViewHolder holder, int position) {
			final String videoPath = mVideoList.get(position).path;
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, DisplayVideoActivity.class);
					intent.putExtra(DisplayVideoActivity.VIDEOPATHKEY, videoPath);
					mContext.startActivity(intent);
				}
			});
			Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, mVideoList.get(position).id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
			String title = mVideoList.get(position).name;
			String status = String.valueOf(mVideoList.get(position).duration);

			holder.mVideoImage.setImageBitmap(bitmap);
			holder.mVideoTitle.setText(title);
			holder.mVideoStatus.setText(status);
		}

		@Override
		public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_myorder_info, null);
			return new ItemViewHolder(itemView);
		}

		public static class ItemViewHolder extends RecyclerView.ViewHolder {
			public ImageView mVideoImage;
			public TextView mVideoTitle;
			public TextView mVideoStatus;
			public ItemViewHolder(View view) {
				super(view);
				mVideoImage = (ImageView) view.findViewById(R.id.video_image);
				mVideoTitle = (TextView)view.findViewById(R.id.video_title);
				mVideoStatus = (TextView)view.findViewById(R.id.video_status);
			}
		}
	}

	/**
	 * 获取本机视频列表
	 * @return
	 */
	private List<Video> getVideos() {
		List<Video> videos = new ArrayList<Video>();
		Cursor c = null;
		try {
			c = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
					null);
			while (c.moveToNext()) {
				String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
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
