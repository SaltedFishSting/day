package com.orange.day.model;


public class UserInfo {

    public UserInfo(){}


    public UserInfo(long uuid, String userName, String userPhone, String userPassword) {
        this.uuid = uuid;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
    }

    private long uuid;

    private String userName;

    private String userPhone;

    private String userPassword;

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return userPassword;
    }

    public void setPassword(String password) {
        userPassword = password;
    }
}
