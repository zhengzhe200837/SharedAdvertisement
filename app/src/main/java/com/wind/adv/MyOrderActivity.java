package com.wind.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sharedadvertisement.wind.com.sharedadvertisement.ChangeClarityActivity;
import sharedadvertisement.wind.com.sharedadvertisement.DisplayVideoActivity;
import sharedadvertisement.wind.com.sharedadvertisement.R;
import utils.CommonUtil;
import utils.VideoInfo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.network.Network;
import com.network.model.MyOrderItemInfo;
import com.network.model.VideoUrl;

public class MyOrderActivity extends Activity {
//	private List<VideoInfo> mList;
	private List<MyOrderItemInfo> mList;
	private RecyclerView mRecyclerView;
	private ContentResolver mContentResolver;
	private MyOrderActivity.RecyclerViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		mContentResolver = getContentResolver();
//		mList = new ArrayList<VideoInfo>();
//		for(int i = 1; i <= CommonUtil.ORDER_TOTAL_SIZE; i++) {
//			mList.add(CommonUtil.getVideoInfo(this, i));
//		}
		mList = new ArrayList<MyOrderItemInfo>();
		mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new MyOrderActivity.RecyclerViewAdapter(this);
		adapter.setVideoList(mList);
		adapter.setContentResolver(mContentResolver);
		mRecyclerView.setAdapter(adapter);
		getDataFromNetwork();
	}

	private void getDataFromNetwork() {
		Toast.makeText(this, "开始获取我的订单", Toast.LENGTH_SHORT).show();
		Network.getMyOrder().getMyOrder()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<List<MyOrderItemInfo>>() {
					@Override
					public void accept(List<MyOrderItemInfo> myOrderItemInfos) throws Exception {
						for (int i = 0; i < myOrderItemInfos.size(); i++) {
							android.util.Log.d("zz", "name = " + myOrderItemInfos.get(i).getVideoName());
							android.util.Log.d("zz", "url = " + myOrderItemInfos.get(i).getVideoUrl());
							android.util.Log.d("zz", "status = " + myOrderItemInfos.get(i).getStatus());
						}
						adapter.setVideoList(myOrderItemInfos);
						adapter.notifyDataSetChanged();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						android.util.Log.d("zz", "error = " + throwable.toString());
					}
				});
	}

	public static class RecyclerViewAdapter extends RecyclerView.Adapter<MyOrderActivity.RecyclerViewAdapter.ItemViewHolder> {
		private Context mContext;
		private List<MyOrderItemInfo> mVideoList;
		private ContentResolver mContentResolver;

		public RecyclerViewAdapter(Context context) {
			mContext = context;
		}
		public void setVideoList(List<MyOrderItemInfo> list) {
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
//			final String videoPath = mVideoList.get(position).getPath();
			final String videoPath = mVideoList.get(position).getVideoUrl();
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					Intent intent = new Intent(mContext, DisplayVideoActivity.class);
//					intent.putExtra(DisplayVideoActivity.VIDEOPATHKEY, videoPath);
//					mContext.startActivity(intent);
					Intent intent = new Intent(mContext, ChangeClarityActivity.class);
					intent.putExtra(ChangeClarityActivity.VIDEOPATH, videoPath);
					mContext.startActivity(intent);
				}
			});
			Bitmap bitmap = null;
			String title = null;
			String status = null;
			if (mVideoList.get(position) != null) {
//				bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, mVideoList.get(position).getId(), MediaStore.Video.Thumbnails.MICRO_KIND, null);
//				title = mVideoList.get(position).getName();
				title = mVideoList.get(position).getVideoName();
				status = mVideoList.get(position).getStatus();
			}

			holder.mVideoImage.setImageBitmap(getVideoFirstFrame(videoPath));
			holder.mVideoTitle.setText(title);
			holder.mVideoStatus.setText(status);
		}

		private Bitmap getVideoFirstFrame(String path) {
			MediaMetadataRetriever mmr = new MediaMetadataRetriever();
			if (path.startsWith("http")) {
				mmr.setDataSource(path, new HashMap<String, String>());  //获取网络视频
			} else {
				mmr.setDataSource(path);  //播放本地视频
			}
			String time = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
			Bitmap bitmap = mmr.getFrameAtTime(1,MediaMetadataRetriever.OPTION_CLOSEST_SYNC); // 获取指定时间点的帧图片  单位是微秒
			return bitmap;
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
