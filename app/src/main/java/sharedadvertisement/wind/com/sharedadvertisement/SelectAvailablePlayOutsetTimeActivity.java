package sharedadvertisement.wind.com.sharedadvertisement;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.calendar.ZzHorizontalCalenderView;
import com.calendar.adapter.TimeSegmentAdapter;
import com.calendar.utils.ViewUtil;

/**
 * Created by zhengzhe on 2017/12/15.
 */

public class SelectAvailablePlayOutsetTimeActivity extends Activity {
    private ZzHorizontalCalenderView view;
    private RecyclerView mTimeSegmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_available_play_outset_time_layout);
        mTimeSegmentList = (RecyclerView)findViewById(R.id.time_segment_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mTimeSegmentList.setLayoutManager(llm);

        for(int i=0; i < 24; i++) {
            android.util.Log.d("zz", TimeSegmentAdapter.getsegmentText(i) + "--" + TimeSegmentAdapter.getsegmentText(i + 1) + " i = " + i);
        }



        view = (ZzHorizontalCalenderView) findViewById(R.id.zz_horizontal_calender_view);
        ViewUtil.scaleContentView((ViewGroup)view);
        view.setOnDaySelectedListener(new ZzHorizontalCalenderView.OnDaySelectedListener() {
            @Override
            public void onSelected(boolean hasChanged, int year, int month, int day, int week) {
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
            }

            @Override
            public void onMonthClick(int selectedYear, int selectedMonth) {
            }
        });
    }
}
