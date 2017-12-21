package sharedadvertisement.wind.com.sharedadvertisement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.network.Network;
import com.network.model.AdvertisementBoardDetailInfo;
import com.network.model.PostModelOfGetBillBoardDetailInfo;
import com.wind.adv.AdvancedOptionsActivity;

import org.w3c.dom.Text;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhengzhe on 2017/11/13.
 */

public class AdvertisementBoardDetailInfoActivity extends Activity {
    private TextView mPublish;
    private RecyclerView mRecyclerView;
    private String[] mApparatusDetail;
    private TextView mCancel;
    private String[] mApparatusDetailValue = new String[5];
    private AdvertisementBoardDetailInfo mCurrentAdvertisementBoardDetailInfo;
    private TextView mBillBoardName;
    private TextView mBillBoardPrice;
    private TextView mBillBoardLocation;
    private TextView mBillBoardPhone;
    private ImageView mBillBoardPicture;
    private RecyclerViewAdapter mAdapter;
    private String mSelectedBillBoardId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement_board_detail_info_layout);
        mBillBoardName = (TextView)findViewById(R.id.bill_board_name);
        mBillBoardPrice = (TextView)findViewById(R.id.bill_board_price);
        mBillBoardLocation = (TextView)findViewById(R.id.bill_board_location);
        mBillBoardPhone = (TextView)findViewById(R.id.bill_board_phone);
        mBillBoardPicture = (ImageView)findViewById(R.id.picture);
        mSelectedBillBoardId = getIntent().getStringExtra(MainActivity.SELECTEDBILLBOARDID);
        mCurrentAdvertisementBoardDetailInfo = getIntent().getParcelableExtra(MainActivity.CURRENTADVERTISEMENTBOARDDETAILINFO);
        if (mCurrentAdvertisementBoardDetailInfo != null) {
            fillDataToViews(mCurrentAdvertisementBoardDetailInfo);
            fillDataToRecyclerView(mCurrentAdvertisementBoardDetailInfo);
        } else {
            android.util.Log.d("zz", "AdvertisementBoardDetailInfoActivity + mCurrentAdvertisementBoardDetailInfo == null");
        }
        if (mCurrentAdvertisementBoardDetailInfo == null) {
            getAdvertisementBoardDetailInfo();
        }
        mApparatusDetail = getResources().getStringArray(R.array.abdi_apparatus_detail);
        mCancel = (TextView) findViewById(R.id.adbi_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPublish = (TextView)findViewById(R.id.abdi_publish);
        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvertisementBoardDetailInfoActivity.this, AdvancedOptionsActivity.class);
                if (mCurrentAdvertisementBoardDetailInfo != null) {
                    intent.putExtra(MainActivity.CHARGECRITERION, mCurrentAdvertisementBoardDetailInfo.getPrice());
                }
                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapter(this);
        mAdapter.setApparatusDetail(mApparatusDetail);
        mAdapter.setApparatusDetailValue(mApparatusDetailValue);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void fillDataToViews(AdvertisementBoardDetailInfo data) {
        mBillBoardPrice.setText(String.valueOf(data.getPrice()) + "元/秒");
        mBillBoardLocation.setText(data.getAddress());
        mBillBoardPhone.setText(data.getBusinessPhone());
        Glide.with(this).load(data.getPicture_url()).into(mBillBoardPicture);
    }

    private void fillDataToRecyclerView(AdvertisementBoardDetailInfo data) {
        mApparatusDetailValue[0] = data.getEquipmentType();
        mApparatusDetailValue[1] = data.getEquipmentAttribute();
        mApparatusDetailValue[2] = data.getScreenType();
        mApparatusDetailValue[3] = data.getScreenAttritute();
        mApparatusDetailValue[4] = String.valueOf(data.getScreenHeight()) + "cm x " + String.valueOf(data.getScreenWidth()) + "cm";
    }

    private void getAdvertisementBoardDetailInfo() {
        PostModelOfGetBillBoardDetailInfo body = new PostModelOfGetBillBoardDetailInfo(mSelectedBillBoardId);
        Network.getAdvertisementBoardDetailInfoApi().getAdvertisementBoardDetailInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AdvertisementBoardDetailInfo>() {
                    @Override
                    public void accept(AdvertisementBoardDetailInfo advertisementBoardDetailInfo) throws Exception {
                        mCurrentAdvertisementBoardDetailInfo = advertisementBoardDetailInfo;
                        fillDataToViews(advertisementBoardDetailInfo);
                        fillDataToRecyclerView(advertisementBoardDetailInfo);
                        mAdapter.setApparatusDetailValue(mApparatusDetailValue);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public static class RecyclerViewAdapter extends RecyclerView.Adapter<AdvertisementBoardDetailInfoActivity.RecyclerViewAdapter.ItemViewHolder> {
        private Context mContext;
        private String[] mApparatusDetail;
        private String[] mApparatusDetailValue;

        public RecyclerViewAdapter(Context context) {
            mContext = context;
        }

        public void setApparatusDetail(String[] ad) {
            mApparatusDetail = ad;
        }

        public void setApparatusDetailValue(String[] adv) {
            mApparatusDetailValue = adv;
        }

        @Override
        public int getItemCount() {
            return mApparatusDetail.length;
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.mItemName.setText(mApparatusDetail[position]);
            holder.mItemValue.setText(mApparatusDetailValue[position]);
            if (position == getItemCount() - 1) {
                holder.mDivider.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.abdi_apparatus_detail_item_layout, null);
            return new ItemViewHolder(itemView);
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            public TextView mItemName;
            public TextView mItemValue;
            public View mDivider;
            public ItemViewHolder(View view) {
                super(view);
                mItemName = (TextView)view.findViewById(R.id.item_name);
                mItemValue = (TextView)view.findViewById(R.id.item_value);
                mDivider = (View)view.findViewById(R.id.item_divider);
            }
        }
    }
}
