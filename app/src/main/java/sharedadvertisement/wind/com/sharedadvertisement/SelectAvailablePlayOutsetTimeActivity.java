package sharedadvertisement.wind.com.sharedadvertisement;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calendar.ZzHorizontalCalenderView;
import com.calendar.utils.ViewUtil;

/**
 * Created by zhengzhe on 2017/12/15.
 */

public class SelectAvailablePlayOutsetTimeActivity extends Activity {
    private ZzHorizontalCalenderView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_available_play_outset_time_layout);
        ViewUtil.scaleContentView((ViewGroup) findViewById(R.id.root));
        final TextView tvResult = (TextView) findViewById(R.id.tv_result);
        view = (ZzHorizontalCalenderView) findViewById(R.id.zz_horizontal_calender_view);
        view.setOnDaySelectedListener(new ZzHorizontalCalenderView.OnDaySelectedListener() {
            @Override
            public void onSelected(boolean hasChanged, int year, int month, int day, int week) {
                tvResult.setText("日期是否有变化:" + hasChanged + ",\n\n日期:" + year + "-" + month + "-" + day + ",\n\n星期:" + week);
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
                tvResult.setText("点击了年:("+selectedYear+","+selectedMonth+")");
            }

            @Override
            public void onMonthClick(int selectedYear, int selectedMonth) {
                tvResult.setText("点击了月 :("+selectedYear+","+selectedMonth+")");
            }
        });
    }
}
