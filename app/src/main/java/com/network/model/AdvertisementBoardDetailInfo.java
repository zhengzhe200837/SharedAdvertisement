package com.network.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class AdvertisementBoardDetailInfo implements Parcelable{
    private long billboardId;
    private long price;
    private String address;
    private long businessId;
    private String equipmentType;
    private String screenType;
    private long screenWidth;
    private long screenHeight;
    private String equipmentName;
    private long openStartTime;
    private long openEndTime;
    private String businessPhone;
    private String tableName;
    private String picture_url;

    private String equipmentAttribute;
    private String screenAttritute;

    public AdvertisementBoardDetailInfo(long price, String address, long businessId,String equipmentType,
                         String screenType, long screenWidth, long screenHeight, long openStartTime,
                         long openEndTime, String businessPhone, String equipmentName, String tableName, String picture_url,
                         String equipmentAttribute, String screenAttritute) {
        this.price = price;
        this.address = address;
        this.businessId = businessId;
        this.equipmentType = equipmentType;
        this.screenType = screenType;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.openStartTime = openStartTime;
        this.openEndTime = openEndTime;
        this.businessPhone = businessPhone;
        this.equipmentName = equipmentName;
        this.tableName = tableName;
        this.picture_url = picture_url;

        this.equipmentAttribute = equipmentAttribute;
        this.screenAttritute = screenAttritute;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(price);
        out.writeString(address);
        out.writeString(equipmentType);
        out.writeString(screenType);
        out.writeLong(screenWidth);
        out.writeLong(screenHeight);
        out.writeLong(openStartTime);
        out.writeLong(openEndTime);
        out.writeString(businessPhone);
        out.writeString(equipmentName);
        out.writeString(picture_url);
        out.writeString(equipmentAttribute);
        out.writeString(screenAttritute);
    }
    public static final Parcelable.Creator<AdvertisementBoardDetailInfo> CREATOR =
            new Parcelable.Creator<AdvertisementBoardDetailInfo>() {
                @Override
                public AdvertisementBoardDetailInfo createFromParcel(Parcel in) {
                    return new AdvertisementBoardDetailInfo(in);
                }
                @Override
                public AdvertisementBoardDetailInfo[] newArray(int size) {
                    return new AdvertisementBoardDetailInfo[size];
                }
            };
    private AdvertisementBoardDetailInfo(Parcel in) {
        price = in.readLong();
        address = in.readString();
        equipmentType = in.readString();
        screenType = in.readString();
        screenWidth = in.readLong();
        screenHeight = in.readLong();
        openStartTime = in.readLong();
        openEndTime = in.readLong();
        businessPhone = in.readString();
        equipmentName = in.readString();
        picture_url = in.readString();
        equipmentAttribute = in.readString();
        screenAttritute = in.readString();
    }

    public String getEquipmentAttribute() {
        return equipmentAttribute;
    }
    public String getScreenAttritute() {
        return screenAttritute;
    }
    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }
    public String getPicture_url() {
        return picture_url;
    }
    public String getEquipmentName() {
        return equipmentName;
    }
    public String getBusinessPhone() {
        return businessPhone;
    }
    public void setOpenStartTime(long openStartTime) {
        this.openStartTime = openStartTime;
    }
    public long getOpenStartTime() {
        return openStartTime;
    }
    public void setOpenEndTime(long openEndTime) {
        this.openEndTime = openEndTime;
    }
    public long getOpenEndTime() {
        return openEndTime;
    }
    public long getBillboardId() {
        return this.billboardId;
    }
    public void setBillboardId(long id) {
        this.billboardId = id;
    }
    public long getBusinessId() {
        return this.businessId;
    }
    public void setbusinessId(long id) {
        this.businessId = id;
    }
    public long getPrice() {
        return this.price;
    }
    public void setPrice(long price) {
        this.price = price;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEquipmentType() {
        return this.equipmentType;
    }
    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }
    public String getScreenType() {
        return this.screenType;
    }
    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }
    public long getScreenWidth() {
        return this.screenWidth;
    }
    public void setScreenWidth(long width) {
        this.screenWidth = width;
    }
    public long getScreenHeight() {
        return this.screenHeight;
    }
    public void setScreenHeight(long height) {
        this.screenHeight = height;
    }
}
