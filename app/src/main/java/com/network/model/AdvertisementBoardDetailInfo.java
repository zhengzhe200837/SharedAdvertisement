package com.network.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class AdvertisementBoardDetailInfo implements Parcelable{
    private String billboardId;
    private long price;
    private String address;
    private String equipmentType;
    private String screenType;
    private long screenWidth;
    private long screenHeight;

    private String startDate;  //开始年月日
    private String endDate;    //结束年月日
    private String startTime;  //每天开始的时分秒
    private String endTime;    //每天结束的时分秒

    private String businessPhone;
    private String pictureUrl;
    private String equipmentAttribute;
    private String screenAttritute;
    private String equipmentName;

    public AdvertisementBoardDetailInfo(String billboardId, long price, String address, String equipmentType,
                                        String screenType, long screenWidth, long screenHeight, String businessPhone,
                                        String startDate, String endDate, String startTime, String endTime,
                                        String pictureUrl, String equipmentAttribute, String screenAttritute,
                                        String equipmentName) {
        this.billboardId = billboardId;
        this.price = price;
        this.address = address;
        this.equipmentType = equipmentType;
        this.screenType = screenType;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.businessPhone = businessPhone;

        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;

        this.pictureUrl = pictureUrl;
        this.equipmentAttribute = equipmentAttribute;
        this.screenAttritute = screenAttritute;
        this.equipmentName = equipmentName;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(billboardId);
        out.writeLong(price);
        out.writeString(address);
        out.writeString(equipmentType);
        out.writeString(screenType);
        out.writeLong(screenWidth);
        out.writeLong(screenHeight);
        out.writeString(businessPhone);
        out.writeString(pictureUrl);
        out.writeString(equipmentAttribute);
        out.writeString(screenAttritute);
        out.writeString(equipmentName);
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
        billboardId = in.readString();
        price = in.readLong();
        address = in.readString();
        equipmentType = in.readString();
        screenType = in.readString();
        screenWidth = in.readLong();
        screenHeight = in.readLong();
        businessPhone = in.readString();
        pictureUrl = in.readString();
        equipmentAttribute = in.readString();
        screenAttritute = in.readString();
        equipmentName = in.readString();
    }

    public String getEquipmentAttribute() {
        return equipmentAttribute;
    }
    public String getScreenAttritute() {
        return screenAttritute;
    }
    public void setPicture_url(String picture_url) {
        this.pictureUrl = picture_url;
    }
    public String getPicture_url() {
        return pictureUrl;
    }
    public String getBusinessPhone() {
        return businessPhone;
    }
    public String getBillboardId() {
        return this.billboardId;
    }
    public void setBillboardId(String id) {
        this.billboardId = id;
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
    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String toString() {
        return "billboardId = " + billboardId + " price = " + price + " address = " + address + " equipmentType = " + equipmentType
                + " screenType = " + screenType + " screenWidth = " + screenWidth + " screenHeight = " + screenHeight
                + " startDate = " + startDate + " endDate = " + endDate + " startTime = " + startTime + " endTime = " + endTime
                + " businessPhone = " + businessPhone + " pictureUrl = " + pictureUrl + " equipmentAttribute = " + equipmentAttribute
                + " screenAttritute = " + screenAttritute + " equipmentName = " + equipmentName;
    }
}
