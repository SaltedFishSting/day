package com.orange.day.model;

public enum  ResultCode {


    SUCCESS("0000","成功"),
    FAIL("0099","服务器繁忙"),
    PARAMENT_ILLEGAL("0001","参数不合法");



    public final String code;
    public final String desc;

    ResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
