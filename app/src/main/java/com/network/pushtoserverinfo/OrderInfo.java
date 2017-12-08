package com.network.pushtoserverinfo;

/**
 * Created by zhengzhe on 2017/12/8.
 */

public class OrderInfo {
    private long orderId;
    private long userId;
    private long billBoardId;
    private long playStartTime;
    private long durationTime;
    private int playTimes;
    private int orderStatus;
    private String videoPath;

    public void setOrderInfo(long userId, long billBoardId, long playStartTime, long durationTime,
                             int playTimes, int orderStatus, String videoPath){
        this.userId = userId;
        this.billBoardId = billBoardId;
        this.playStartTime = playStartTime;
        this.durationTime = durationTime;
        this.playTimes = playTimes;
        this.orderStatus = orderStatus;
        this.videoPath = videoPath;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long id) {
        this.orderId = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public long getBillBoardId() {
        return this.billBoardId;
    }

    public void setBillBoardId(long id) {
        this.billBoardId = id;
    }

    public long getPlayStartTime() {
        return this.playStartTime;
    }

    public void setPlayStartTime(long time) {
        this.playStartTime = time;
    }

    public long getDurationTime() {
        return this.durationTime;
    }

    public void setDurationTime(long time) {
        this.durationTime = time;
    }

    public long getPlayTimes() {
        return this.playTimes;
    }

    public void setPlayTimes(int times) {
        this.playTimes = times;
    }

    public long getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(int status) {
        this.orderStatus = status;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public void setVideoPath(String path) {
        this.videoPath = path;
    }

//	@Override
//	public String toString() {
//		return String.format("id:%d,name:%s,description:%s", id, name, description);
//	}

}

