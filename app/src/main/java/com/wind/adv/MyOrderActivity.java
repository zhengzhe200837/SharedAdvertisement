package com.wind.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sharedadvertisement.wind.com.sharedadvertisement.ChangeClarityActivity;
import sharedadvertisement.wind.com.sharedadvertisement.R;
import utils.LogUtil;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.network.Network;
import com.network.model.MyOrderItemInfo;
import com.network.model.PostModelOfGetMyOrder;

public class MyOrderActivity extends Activity {
	private List<MyOrderItemInfo> mList;
	private RecyclerView mRecyclerView;
	private ContentResolver mContentResolver;
	private MyOrderActivity.RecyclerViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		mContentResolver = getContentResolver();
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
		PostModelOfGetMyOrder order = new PostModelOfGetMyOrder(getUserPhone());
		Network.getMyOrder().getMyOrder(order)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<List<MyOrderItemInfo>>() {
					@Override
					public void accept(List<MyOrderItemInfo> myOrderItemInfos) throws Exception {
						for (int i = 0; i < myOrderItemInfos.size(); i++) {
							LogUtil.d("MyOrderActivity + getDataFromNetwork() + myOrderItemInfo = " + myOrderItemInfos.get(i).toString());
						}
						adapter.setVideoList(myOrderItemInfos);
						adapter.notifyDataSetChanged();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						LogUtil.d("MyOrderActivity + getDataFromNetwork() + error = " + throwable.toString());
					}
				});
	}

	private String getUserPhone() {
		SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
		String phone = sp.getString("user_phone", "");
		return phone;
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
			int status = -1;
			if (mVideoList.get(position) != null) {
//				bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, mVideoList.get(position).getId(), MediaStore.Video.Thumbnails.MICRO_KIND, null);
//				title = mVideoList.get(position).getName();
				title = mVideoList.get(position).getVideoName();
				status = mVideoList.get(position).getStatus();
			}

			holder.mVideoImage.setImageBitmap(getVideoFirstFrame(videoPath));
			holder.mVideoTitle.setText(title);

			String statusS;
			if (status == 0) {  //0审核通过还未播放，1已播放，2未审核，3审核不通过，4审核通过未上传
				statusS = "审核通过还未播放";
			} else if (status == 1) {
				statusS = "已播放";
			} else if (status == 2) {
				statusS = "未审核";
			} else if (status == 3) {
				statusS = "审核不通过";
			} else if (status == 4) {
				statusS = "审核通过未上传";
			} else {
				statusS = "无效";
			}

			holder.mVideoStatus.setText(statusS);
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
