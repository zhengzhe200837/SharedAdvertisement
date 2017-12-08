package com.network.pushtoserverinfo;

/**
 * Created by zhengzhe on 2017/12/8.
 */

public class UserInfo {
    private long userId;
    private String phoneNumber;

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
