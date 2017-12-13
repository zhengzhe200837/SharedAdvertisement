package com.network.model;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class UploadMyOrderInfo {
    private String tableName;
    private int userId;
    private int billBoardId;
    private int playStartYear;
    private int playStartMonth;
    private int playStartDate;
    private int playStartHour;
    private int playStartMinute;
    private long totalPrice;
    private int durationTime;
    private int playTimes;
    private int orderStatus;

    public UploadMyOrderInfo(String tableName, int userId, int billBoardId, int playStartYear,
                             int playStartMonth, int playStartDate, int playStartHour, int playStartMinute,
                             long totalPrice, int durationTime, int playTimes,
                             int orderStatus) {
        this.tableName = tableName;
        this.userId = userId;
        this.billBoardId = billBoardId;
        this.playStartYear = playStartYear;
        this.playStartMonth = playStartMonth;
        this.playStartDate = playStartDate;
        this.playStartHour = playStartHour;
        this.playStartMinute = playStartMinute;
        this.totalPrice = totalPrice;
        this.durationTime = durationTime;
        this.playTimes = playTimes;
        this.orderStatus = orderStatus;
    }

    public static class UploadMyOrderInfoBuilder{
        private String tableName;
        private int userId;
        private int billBoardId;
        private int playStartYear;
        private int playStartMonth;
        private int playStartDate;
        private int playStartHour;
        private int playStartMinute;
        private long totalPrice;
        private int durationTime;
        private int playTimes;
        private int orderStatus;
        public UploadMyOrderInfoBuilder() {
        }
        public UploadMyOrderInfo build() {
            return new UploadMyOrderInfo(tableName, userId, billBoardId, playStartYear,
                playStartMonth, playStartDate, playStartHour, playStartMinute,
                totalPrice, durationTime, playTimes, orderStatus);
        }

        public UploadMyOrderInfoBuilder setTotalPrice(long totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }
        public UploadMyOrderInfoBuilder setPlayStartMinute(int playStartMinute) {
            this.playStartMinute = playStartMinute;
            return this;
        }
        public UploadMyOrderInfoBuilder setPlayStartHour(int playStartHour) {
            this.playStartHour = playStartHour;
            return this;
        }
        public UploadMyOrderInfoBuilder setPlayStartDate(int playStartDate) {
            this.playStartDate = playStartDate;
            return this;
        }
        public UploadMyOrderInfoBuilder setPlayStartMonth(int playStartMonth) {
            this.playStartMonth = playStartMonth;
            return this;
        }
        public UploadMyOrderInfoBuilder setPlayStartYear(int playStartYear) {
            this.playStartYear = playStartYear;
            return this;
        }
        public UploadMyOrderInfoBuilder setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }
        public UploadMyOrderInfoBuilder setUserId(int userId) {
            this.userId = userId;
            return this;
        }
        public UploadMyOrderInfoBuilder setBillBoardId(int billBoardId) {
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
}
