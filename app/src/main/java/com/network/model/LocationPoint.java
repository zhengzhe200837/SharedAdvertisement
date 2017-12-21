package com.network.model;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class LocationPoint {
    private double latitude;
    private double longitude;
    private String billboardId;
    public LocationPoint(double latitude, double longitude, String billboardId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.billboardId = billboardId;
    }
    public String toString() {
        return "latitude = " + latitude + " longitude = " + longitude + " billboardId = " + billboardId;
    }
    public String getBillboardId() {
        return billboardId;
    }

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
}
