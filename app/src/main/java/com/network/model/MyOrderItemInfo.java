package com.network.model;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class MyOrderItemInfo {
    private String name;
    private String url;
    private int order_status;   //订单状态：0审核通过还未播放，1已播放，2未审核，3审核不通过，4审核通过未上传
    private String playTime = null;

    public MyOrderItemInfo(String videoName, String videoUrl, int status, String playTime) {
        this.name = videoName;
        this.url = videoUrl;
        this.order_status = status;
        this.playTime = playTime;
    }

    public String toString() {
        return "name = " + name + " url = " + url + " order_status = " + order_status + " playTime = " + playTime;
    }

    public void setVideoUrl(String video_url) {
        this.url = video_url;
    }
    public String getVideoUrl() {
        return url;
    }
    public void setVideoName(String videoName) {
        this.name = videoName;
    }
    public String getVideoName() {
        return name;
    }
    public void setStatus(int status) {
        this.order_status = status;
    }
    public int getStatus() {
        return order_status;
    }
}
