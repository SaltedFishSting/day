package com.orange.day.model;

import java.util.HashMap;
import java.util.Map;

public class ResultBean {

    private String code;
    private String desc;
    private Map<String, Object> data;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String key, Object object) {
        if (this.data == null) {
            data = new HashMap<>();
        }
        data.put(key, object);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
