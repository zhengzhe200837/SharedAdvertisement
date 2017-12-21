package com.network.model;

/**
 * Created by zhengzhe on 2017/12/20.
 */

public class PostModelOfGetSelectedPlayTimeSegment {
    private String todo = "query";
    private String tableName = "billBoardOrderedInfo";
    private String date;  //年月日
    private int time_status = 1;  //已经被预定
    private String billboardId = null;
    public PostModelOfGetSelectedPlayTimeSegment(String date, String billboardId) {
        this.date = date;
        this.billboardId = billboardId;
    }
}
