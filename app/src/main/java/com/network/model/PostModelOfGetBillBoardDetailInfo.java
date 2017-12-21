package com.network.model;

/**
 * Created by zhengzhe on 2017/12/20.
 */

public class PostModelOfGetBillBoardDetailInfo {
    private String todo = "query";
    private String tableName = "billBoardInfo";
    private String billboardId = null;

    public PostModelOfGetBillBoardDetailInfo(String billboardId) {
        this.billboardId = billboardId;
    }
}
