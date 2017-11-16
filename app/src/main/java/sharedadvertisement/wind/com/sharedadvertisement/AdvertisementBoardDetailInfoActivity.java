package sharedadvertisement.wind.com.sharedadvertisement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by zhengzhe on 2017/11/13.
 */

public class AdvertisementBoardDetailInfoActivity extends Activity {
    private TextView mPublish;
    private RecyclerView mRecyclerView;
    private String[] mApparatusDetail;
    private TextView mCancel;
    private String[] mApparatusDetailValue = new String[] {
            "固定设备", "餐馆", "LED", "横屏", "1300cm x 750cm"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement_board_detail_info_layout);
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

            }
        });
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter rva = new RecyclerViewAdapter(this);
        rva.setApparatusDetail(mApparatusDetail);
        rva.setApparatusDetailValue(mApparatusDetailValue);
        mRecyclerView.setAdapter(rva);
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
