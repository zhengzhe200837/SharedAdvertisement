package com.calendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import sharedadvertisement.wind.com.sharedadvertisement.R;

/**
 * Created by zhengzhe on 2017/12/16.
 */

public class TimeSegmentAdapter extends RecyclerView.Adapter<TimeSegmentAdapter.ItemViewHolder> {
    private Context mContext;

    public TimeSegmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return 2*12;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.time_segment_item_layout, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView segmentText;
        public LinearLayout segmentDetail;
        public ItemViewHolder(View view) {
            super(view);
            segmentText = (TextView)view.findViewById(R.id.segment_text);
            segmentDetail = (LinearLayout)view.findViewById(R.id.segment_detail);
        }
    }

    public static String getsegmentText(int segment) {
        long totalSeconds = 2*60*60;
        int segmentSeconds = 5*60;
        int totalSegment = (int)(totalSeconds/segmentSeconds);
        String segmentText = null;
        int startHour = 18;
        int startMinute = 0;
        int startSecond = 0;
        String endHour;
        String endMinute;
        String endSecond;
        long elapseSeconds = segment * segmentSeconds;
        if (elapseSeconds/(60*60) > 0) {
            startHour += elapseSeconds/(60*60);
        }
        if ( (elapseSeconds%(60*60))/60 > 0  ) {
            startMinute += (elapseSeconds%(60*60))/60;
            startSecond += (elapseSeconds%(60*60))%60;
        }
        String minute = null;
        String second = null;
        if (startMinute < 10) {
            minute = "" + "0" + startMinute;
        } else {
            minute = "" + startMinute;
        }
        if (startSecond < 10) {
            second = "" + "0" + startSecond;
        } else {
            second = "" + startSecond;
        }
        segmentText = "" + startHour + ":" + minute + ":" + second;
//        android.util.Log.d("zz", "segmentText = " + segmentText);
        return segmentText;
    }
}
