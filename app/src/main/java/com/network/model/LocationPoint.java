package com.network.model;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class LocationPoint {
    private double latitude;
    private double longitude;
    private String detail_url;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }
    public String getDetail_url() {
        return detail_url;
    }
}
