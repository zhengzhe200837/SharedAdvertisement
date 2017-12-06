package com.network.model;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class MyOrderItemInfo {
    private String videoName;
    private String videoUrl;
    private String status;

    public MyOrderItemInfo(String videoName, String videoUrl, String status) {
        this.videoName = videoName;
        this.videoUrl = videoUrl;
        this.status = status;
    }

    public void setVideoUrl(String video_url) {
        this.videoUrl = video_url;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
    public String getVideoName() {
        return videoName;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
