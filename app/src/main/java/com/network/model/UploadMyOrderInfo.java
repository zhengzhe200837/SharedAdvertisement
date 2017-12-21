package com.network.model;

import java.util.List;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class UploadMyOrderInfo {
    private String todo;
    private String tableName;
    private String orderId;
    private String billBoardId;
    private long totalPrice;
    private int durationTime;
    private int playTimes;
    private int orderStatus;
    private String userPhone;
    private String playStartTime;  //年月日时分秒
    private String mediaName;
    public  String businessPhone;

    public UploadMyOrderInfo(String todo, String tableName, String orderId, String billBoardId, long totalPrice, int durationTime, int playTimes,
                             int orderStatus, String userPhone, String playStartTime, String mediaName, String businessPhone) {
        this.todo = todo;
        this.tableName = tableName;
        this.orderId = orderId;
        this.billBoardId = billBoardId;
        this.totalPrice = totalPrice;
        this.durationTime = durationTime;
        this.playTimes = playTimes;
        this.orderStatus = orderStatus;
        this.userPhone = userPhone;
        this.playStartTime = playStartTime;
        this.mediaName = mediaName;
        this.businessPhone = businessPhone;
    }

    public static class UploadMyOrderInfoBuilder{
        private String todo;
        private String tableName;
        private String orderId;
        private String billBoardId;
        private long totalPrice;
        private int durationTime;
        private int playTimes;
        private int orderStatus;
        private String userPhone;
        private String playStartTime;  //年月日时分秒
        private String mediaName;
        private String businessPhone;

        public UploadMyOrderInfoBuilder() {
        }
        public UploadMyOrderInfo build() {
            return new UploadMyOrderInfo(todo, tableName, orderId, billBoardId, totalPrice,
                    durationTime, playTimes, orderStatus, userPhone, playStartTime,
                    mediaName, businessPhone);
        }
        public UploadMyOrderInfoBuilder setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public UploadMyOrderInfoBuilder setTodo(String todo) {
            this.todo = todo;
            return this;
        }

        public UploadMyOrderInfoBuilder setBusinessPhone(String businessPhone) {
            this.businessPhone = businessPhone;
            return this;
        }

        public UploadMyOrderInfoBuilder setMediaName(String mediaName) {
            this.mediaName = mediaName;
            return this;
        }

        public UploadMyOrderInfoBuilder setPlayStartTime(String playStartTime) {
            this.playStartTime = playStartTime;
            return this;
        }

        public UploadMyOrderInfoBuilder setUserPhone(String userPhone) {
            this.userPhone = userPhone;
            return this;
        }

        public UploadMyOrderInfoBuilder setTotalPrice(long totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }
        public UploadMyOrderInfoBuilder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }
        public UploadMyOrderInfoBuilder setBillBoardId(String billBoardId) {
            this.billBoardId = billBoardId;
            return this;
        }
        public UploadMyOrderInfoBuilder setDurationTime(int durationTime) {
            this.durationTime = durationTime;
            return this;
        }
        public UploadMyOrderInfoBuilder setPlayTimes(int playTimes) {
            this.playTimes = playTimes;
            return this;
        }
        public UploadMyOrderInfoBuilder setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }
    }

    public String toString() {
        String s = "UploadMyOrderInfo + todo = " + todo + " tableName = " + tableName + " orderId = " + orderId + " durationTime = "
                + durationTime + " billBoardId = " + billBoardId + " totalPrice = " + totalPrice + " playTimes = " + playTimes
                + " orderStatus = " + orderStatus + " userPhone = " + userPhone + " playStartTime = " + playStartTime
                + " mediaName = " + mediaName + " businessPhone = " + businessPhone;
        return s;
    }
}
