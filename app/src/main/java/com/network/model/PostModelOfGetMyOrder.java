package com.network.model;

/**
 * Created by zhengzhe on 2017/12/20.
 */

public class PostModelOfGetMyOrder {
    private String todo = "query";
    private String tableName = "orderInfo";
    private String userPhone = null;
    public PostModelOfGetMyOrder(String userPhone) {
        this.userPhone = userPhone;
    }
}
