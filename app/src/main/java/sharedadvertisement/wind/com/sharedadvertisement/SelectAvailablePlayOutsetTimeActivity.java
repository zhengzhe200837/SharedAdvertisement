package sharedadvertisement.wind.com.sharedadvertisement;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.calendar.ZzHorizontalCalenderView;
import com.calendar.adapter.TimeSegmentAdapter;
import com.calendar.utils.DateUtil;
import com.calendar.utils.ViewUtil;
import com.network.Network;
import com.network.model.PostModelOfGetSelectedPlayTimeSegment;
import com.network.model.SelectedPlayTimeSegment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import utils.LogUtil;

/**
 * Created by zhengzhe on 2017/12/15.
 */

public class SelectAvailablePlayOutsetTimeActivity extends Activity {
    private ZzHorizontalCalenderView view;
    private RecyclerView mTimeSegmentList;
    private List<SelectedPlayTimeSegment> mSelectedPlayTimeSegments = new ArrayList<>();
    private TimeSegmentAdapter mAdapter;
    private int mCurrentYear = DateUtil.getCurrentYear();
    private int mCurrentMonth = DateUtil.getCurrentMonth();
    private int mCurrentDay = DateUtil.getCurrentDay();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_available_play_outset_time_layout);
        mTimeSegmentList = (RecyclerView)findViewById(R.id.time_segment_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mTimeSegmentList.setLayoutManager(llm);
        getStartHour();
        getEndHour();

        getSelectedPlayTimeSegmentFromNetwork(String.valueOf(mCurrentYear), String.valueOf(mCurrentMonth), String.valueOf(mCurrentDay));   //请求网络
        mAdapter = new TimeSegmentAdapter(this, mSelectedPlayTimeSegments, String.valueOf(mCurrentYear),
                String.valueOf(mCurrentMonth), String.valueOf(mCurrentDay), mStartHour, mEndHour);
        mTimeSegmentList.setAdapter(mAdapter);

        view = (ZzHorizontalCalenderView) findViewById(R.id.zz_horizontal_calender_view);
        ViewUtil.scaleContentView((ViewGroup)view);
        view.setOnDaySelectedListener(new ZzHorizontalCalenderView.OnDaySelectedListener() {
            @Override
            public void onSelected(boolean hasChanged, int year, int month, int day, int week) {    //当选择年月后都会回调这个方法，所以在这请求网络
                mCurrentYear = year;
                mCurrentMonth = month;
                mCurrentDay = day;
                getSelectedPlayTimeSegmentFromNetwork(String.valueOf(mCurrentYear), String.valueOf(mCurrentMonth), String.valueOf(mCurrentDay));  //请求网络
                mAdapter.getJustSelectedPlayTimeSegments();
            }
        });
        view.setShowPickDialog(true);
        view.setUnitColorResId(android.R.color.holo_green_dark);
//        view.setDayTextColorSelectedResId(android.R.color.holo_blue_bright);
        view.setDayTextColorNormalResId(android.R.color.holo_red_dark);
        view.setDaySelectionColorResId(android.R.color.holo_orange_dark);
        view.setTodayPointColor(Color.YELLOW);
        view.setMonthTextColor(Color.RED);
        view.setYearTextColor(Color.BLUE);

        view.setOnYearMonthClickListener(new ZzHorizontalCalenderView.OnYearMonthClickListener() {
            @Override
            public void onYearClick(int selectedYear, int selectedMonth) {
                mCurrentYear = selectedYear;
                mCurrentMonth = selectedMonth;
            }

            @Override
            public void onMonthClick(int selectedYear, int selectedMonth) {
                mCurrentYear = selectedYear;
                mCurrentMonth = selectedMonth;
            }
        });
    }

    private int mStartHour;
    private int mEndHour;
    private void getStartHour() {
        SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
        String startTime = sp.getString("startTime", null);   //时分秒
        if (startTime != null) {
            mStartHour = Integer.parseInt(startTime.substring(0, 2));
        }
    }
    private void getEndHour() {
        SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
        String endTime = sp.getString("endTime", null);   //时分秒
        if (endTime != null) {
            mEndHour = Integer.parseInt(endTime.substring(0, 2));
        }
    }

    /**
     * 从网络获取已经被选择的播放时间片段
     * @param year
     * @param month
     * @param day
     */
    private void getSelectedPlayTimeSegmentFromNetwork(String year, String month, String day) {
        if (mAdapter != null) {
            mAdapter.setCurrentYear(year);
            mAdapter.setCurrentMonth(month);
            mAdapter.setCurrentDay(day);
        }
        PostModelOfGetSelectedPlayTimeSegment body = new PostModelOfGetSelectedPlayTimeSegment(year + month + day, getSelectedBillBoardId());
        Network.getSelectedPlayTimeSegment().getSelectedPlayTimeSegment(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SelectedPlayTimeSegment>>() {
                    @Override
                    public void accept(List<SelectedPlayTimeSegment> selectedPlayTimeSegments) throws Exception {
                        for(int i = 0; i < selectedPlayTimeSegments.size(); i++) {
                            LogUtil.d("SelectAvailablePlayOutsetTimeActivity + getSelectedPlayTimeSegmentFromNetwork() + "
                                    + selectedPlayTimeSegments.get(i).toSting());
                        }
                        mSelectedPlayTimeSegments = selectedPlayTimeSegments;
                        mAdapter.setSelectedPlayTimeSegments(selectedPlayTimeSegments);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        android.util.Log.d("zz", "error = " + throwable.toString());
                    }
                });
    }

    private String getSelectedBillBoardId() {
        SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
        String selectedBillBoardId = sp.getString("selectedBillBoardId", null);
        return selectedBillBoardId;
    }
}
