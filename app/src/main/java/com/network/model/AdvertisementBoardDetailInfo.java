package com.network.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class AdvertisementBoardDetailInfo implements Parcelable{
    private int price;
    private String picture_url;

    public AdvertisementBoardDetailInfo(int price, String url) {
        this.price = price;
        this.picture_url = url;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }
    public String getPicture_url() {
        return picture_url;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(price);
        out.writeString(picture_url);
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
        price = in.readInt();
        picture_url = in.readString();
    }
}
