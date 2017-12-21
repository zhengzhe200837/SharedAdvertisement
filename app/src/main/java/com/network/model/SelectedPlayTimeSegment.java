package com.network.model;

/**
 * Created by zhengzhe on 2017/12/18.
 * 每个时间片段的对象表示，30s是一个时间片段，属性表示每个时间片段开始的时，分，秒
 */

public class SelectedPlayTimeSegment {
    private String billboardId;
    private String date;  //年月日
    private String time;  //时分秒
    private int time_status;
    public SelectedPlayTimeSegment(String billboardId, String date, String time, int time_status) {
        this.billboardId = billboardId;
        this.date = date;
        this.time = time;
        this.time_status = time_status;
    }

    public String toSting() {
        return "billboardId = " + billboardId + " date = " + date + " time= " + time + " time_status = " + time_status;
    }

    public String getHour() {
        return time.substring(0, 2);
    }

    public String getMinute() {
        return time.substring(2, 4);
    }

    public String getSecond() {
        return time.substring(4);
    }

//    String year;
//    String month;
//    String day;
//    String hour;
//    String minute;
//    String second;
//    public SelectedPlayTimeSegment(String year, String month, String day,
//                                   String hour, String minute, String second) {
//        this.year = year;
//        this.month = month;
//        this.day = day;
//        this.hour = hour;
//        this.minute = minute;
//        this.second = second;
//    }
//
//    public String toString() {
//        return year + month + day + hour + minute + second;
//    }
//
//    public String getHour() {
//        return hour;
//    }
//
//    public String getMinute() {
//        return minute;
//    }
//
//    public String getSecond() {
//        return second;
//    }
}
