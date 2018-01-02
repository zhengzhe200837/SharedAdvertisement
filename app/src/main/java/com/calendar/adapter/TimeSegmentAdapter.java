package com.calendar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.network.model.SelectedPlayTimeSegment;

import java.util.ArrayList;
import java.util.List;

import sharedadvertisement.wind.com.sharedadvertisement.R;
import sharedadvertisement.wind.com.sharedadvertisement.SelectAvailablePlayOutsetTimeActivity;
import utils.LogUtil;

/**
 * Created by zhengzhe on 2017/12/16.
 */

public class TimeSegmentAdapter extends RecyclerView.Adapter<TimeSegmentAdapter.ItemViewHolder> {
    private Context mContext;
    private List<SelectedPlayTimeSegment> mSelectedPlayTimeSegments;  //已经被选择的片段  从网络获取，构造方法中传递过来
    private List<SelectedPlayTimeSegment> mJustSelectedPlayTimeSegments = new ArrayList<>();  //刚刚被选择的片段，需要提交到服务器
    private String mCurrentYear;
    private String mCurrentMonth;
    private String mCurrentDay;
    private String mMySelectPlayStartTime;
    private int mStartHour;
    private int mEndHour;

    public TimeSegmentAdapter(Context context, List<SelectedPlayTimeSegment> selectedPlayTimeSegments, String currentYear, String currentMonth, String currnentDay,
                                    int startHour, int endHour) {
        this.mContext = context;
        mSelectedPlayTimeSegments = selectedPlayTimeSegments;
        this.mCurrentYear = currentYear;
        this.mCurrentMonth = currentMonth;
        this.mCurrentDay = currnentDay;
        this.mStartHour = startHour;
        this.mEndHour = endHour;
    }

    public void setSelectedPlayTimeSegments(List<SelectedPlayTimeSegment> segments) {
        mSelectedPlayTimeSegments = segments;
    }

    @Override
    public int getItemCount() {
        return (mEndHour - mStartHour) * 12;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.time_segment_item_layout, null);  //在xml文件中设置宽度match_parent无效
        View item = LayoutInflater.from(mContext).inflate(R.layout.time_segment_item_layout, parent, false);
        LinearLayout detail = (LinearLayout)item.findViewById(R.id.segment_detail);
        for(int i = 0; i < 10; i++) {
            View thirtySeconds = LayoutInflater.from(mContext).inflate(R.layout.time_segment_thirty_seconds_layout, detail, false);
            thirtySeconds.setId(i);
            detail.addView(thirtySeconds);
        }
        return new ItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.segmentText.setText(getSegmentText(position) + "-" + getSegmentText(position + 1));
        final String currentHour = getSegmentText(position).substring(0, 2);
        int startMinute = Integer.parseInt(getSegmentText(position).substring(3, 5));
        for (int i = 0; i < 10; i++ ) {
            View thirtySecondItem = holder.segmentDetail.findViewById(i);
            TextView start = (TextView) thirtySecondItem.findViewById(R.id.start);
            TextView end = (TextView) thirtySecondItem.findViewById(R.id.end);
            final String startMinuteSecond = getStartEndMinuteSecond(startMinute, i);
            String endMinuteSecond = getStartEndMinuteSecond(startMinute, i + 1);

            start.setText(startMinuteSecond);
            end.setText(endMinuteSecond);

            //init thirtySecondItem start
            thirtySecondItem.setBackgroundColor(0);
            thirtySecondItem.setEnabled(true);
            //init thirtySecondItem end

            if (isSelectedTime(currentHour, startMinuteSecond, mSelectedPlayTimeSegments)) {
                thirtySecondItem.setBackgroundColor(Color.parseColor("#969696"));
                thirtySecondItem.setEnabled(false);
            }

            if (isSelectedTime(currentHour, startMinuteSecond, mJustSelectedPlayTimeSegments)) {
                thirtySecondItem.setBackgroundColor(Color.parseColor("#00CD00"));
            }

            thirtySecondItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击某项直接退出activvity，将数据返回给上层activity  start
                    if (Integer.parseInt(mCurrentMonth) < 10) {
                        mCurrentMonth = "0" + mCurrentMonth;
                    }
                    if (Integer.parseInt(mCurrentDay) < 10) {
                        mCurrentDay = "0" + mCurrentDay;
                    }
                    mMySelectPlayStartTime = mCurrentYear + mCurrentMonth + mCurrentDay + currentHour + startMinuteSecond.substring(0, 2) + startMinuteSecond.substring(3);
                    v.setBackgroundColor(Color.parseColor("#00CD00"));
                    SelectAvailablePlayOutsetTimeActivity activity = (SelectAvailablePlayOutsetTimeActivity)mContext;
                    Intent intent = new Intent();
                    intent.putExtra("selected_play_start_time", mMySelectPlayStartTime);
                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.finish();
                    android.util.Log.d("zz", "mMySelectPlayStartTime = " + mMySelectPlayStartTime);
                    //点击某项直接退出activvity，将数据返回给上层activity  end

                    //可以选择多项，点击变绿再点击恢复正常  start
//                    SelectedPlayTimeSegment spts = new SelectedPlayTimeSegment(mCurrentYear, mCurrentMonth, mCurrentDay,
//                            currentHour, startMinuteSecond.substring(0, 2), startMinuteSecond.substring(3));
//                    int matchIndex = getMatchSegment(spts, mJustSelectedPlayTimeSegments);
//                    if (matchIndex == -1) {
//                        v.setBackgroundColor(Color.parseColor("#00CD00"));
//                        mJustSelectedPlayTimeSegments.add(spts);
//                    } else {
//                        v.setBackgroundColor(0);
//                        mJustSelectedPlayTimeSegments.remove(matchIndex);
//                    }
                    //可以选择多项，点击变绿再点击恢复正常  end
                }
            });
        }
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

    public void setCurrentYear(String year) {
        mCurrentYear = year;
    }
    public void setCurrentMonth(String month) {
        if (Integer.parseInt(month) < 10) {
            mCurrentMonth = "0" + month;
        } else {
            mCurrentMonth = month;
        }
    }
    public void setCurrentDay(String day) {
        if (Integer.parseInt(day) < 10) {
            mCurrentDay = "0" + day;
        } else {
            mCurrentDay = day;
        }
    }
    public List<SelectedPlayTimeSegment> getJustSelectedPlayTimeSegments() {
        android.util.Log.d("---zz", "size = " + mJustSelectedPlayTimeSegments.size());
        for(int i = 0; i < mJustSelectedPlayTimeSegments.size(); i++) {
            SelectedPlayTimeSegment spts = mJustSelectedPlayTimeSegments.get(i);
            android.util.Log.d("---zz", "hour = " + spts.getHour() + " --" + i);
            android.util.Log.d("---zz", "minute = " + spts.getMinute());
            android.util.Log.d("---zz", "second = " + spts.getSecond());
        }
        return mJustSelectedPlayTimeSegments;
    }

    private int getMatchSegment(SelectedPlayTimeSegment current, List<SelectedPlayTimeSegment> data) {
        for(int i = 0; i < data.size(); i++) {
            if ( current.getHour().equals(data.get(i).getHour()) &&
                    current.getMinute().equals(data.get(i).getMinute()) &&
                    current.getSecond().equals(data.get(i).getSecond())) {
                return i;
            }
        }
        return -1;
    }

    private boolean isSelectedTime(String currentHour, String currentMinuteSecond, List<SelectedPlayTimeSegment> data) {
        for(int i = 0; i < data.size(); i++) {
            if ( currentHour.equals(data.get(i).getHour()) &&
                    currentMinuteSecond.substring(0, 2).equals(data.get(i).getMinute()) &&
                    currentMinuteSecond.substring(3).equals(data.get(i).getSecond())) {
                return true;
            }
        }
        return false;
    }

    private String getStartEndMinuteSecond(int startMinute, int whichSegment) {
        int elapseSeconds = whichSegment * 30;
        int minute = startMinute;
        int second = 0;
        if (elapseSeconds / 60 > 0) {
            minute = minute + elapseSeconds / 60;
            second = elapseSeconds % 60;
        } else {
            second = elapseSeconds;
        }
        String minuteS = null;
        String secondS = null;
        if (minute < 10) {
            minuteS = "0" + minute;
        } else {
            minuteS = "" + minute;
        }
        if (minute == 60) {
            minuteS = "00";
        }
        if (second < 10) {
            secondS = "0" + second;
        } else {
            secondS = "" + second;
        }
        String result = minuteS + ":" + secondS;
        return result;
    }

    private String getSegmentText(int segment) {
        int segmentSeconds = 5*60;
        String segmentText = null;
        int startHour = mStartHour;
        LogUtil.d("TimeSegmentAdapter + getSegmentText() + mStartHour = " + mStartHour);
//        int startHour = 16;
        int startMinute = 0;
        int startSecond = 0;
        long elapseSeconds = segment * segmentSeconds;
        if (elapseSeconds/(60*60) > 0) {
            startHour += elapseSeconds/(60*60);
        }
        if ( (elapseSeconds%(60*60))/60 > 0  ) {
            startMinute += (elapseSeconds%(60*60))/60;
            startSecond += (elapseSeconds%(60*60))%60;
        }
        String hour = null;
        String minute = null;
        String second = null;
        if (startHour < 10) {
            hour = "" + "0" + startHour;
        } else {
            hour = "" + startHour;
        }

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
        segmentText = "" + hour + ":" + minute + ":" + second;
        return segmentText;
    }
}
