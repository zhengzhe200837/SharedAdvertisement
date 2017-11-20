package com.wind.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sharedadvertisement.wind.com.sharedadvertisement.R;
import utils.CommonUtil;
import utils.VideoInfo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MyOrderActivity extends Activity {
	private List<VideoInfo> mList;
	private RecyclerView mRecyclerView;
	private ContentResolver mContentResolver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		mContentResolver = getContentResolver();
		mList = new ArrayList<VideoInfo>();
		for(int i = 1; i <= CommonUtil.ORDER_TOTAL_SIZE; i++) {
			mList.add(CommonUtil.getVideoInfo(this, i));
		}
		mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		MyOrderActivity.RecyclerViewAdapter rva = new MyOrderActivity.RecyclerViewAdapter(this);
		rva.setVideoList(mList);
		rva.setContentResolver(mContentResolver);
		mRecyclerView.setAdapter(rva);
	}

	public static class RecyclerViewAdapter extends RecyclerView.Adapter<MyOrderActivity.RecyclerViewAdapter.ItemViewHolder> {
		private Context mContext;
		private List<VideoInfo> mVideoList;
		private ContentResolver mContentResolver;

		public RecyclerViewAdapter(Context context) {
			mContext = context;
		}
		public void setVideoList(List<VideoInfo> list) {
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
		public void onBindViewHolder(MyOrderActivity.RecyclerViewAdapter.ItemViewHolder holder, int position) {
			Bitmap bitmap = null;
			String title = null;
			String status = null;
			if (mVideoList.get(position) != null) {
				bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, mVideoList.get(position).getId(), MediaStore.Video.Thumbnails.MICRO_KIND, null);
				title = mVideoList.get(position).getName();
				status = mVideoList.get(position).getStatus();
			}

			holder.mVideoImage.setImageBitmap(bitmap);
			holder.mVideoTitle.setText(title);
			holder.mVideoStatus.setText(status);
		}

		@Override
		public MyOrderActivity.RecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_myorder_info, null);
			return new MyOrderActivity.RecyclerViewAdapter.ItemViewHolder(itemView);
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
}
