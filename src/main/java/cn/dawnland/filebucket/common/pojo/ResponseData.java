package cn.dawnland.filebucket.common.pojo;

import com.qcloud.cos.utils.Jackson;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {

    private String code = "0";

    private String message = "SUCCESS";

    private Map data = new HashMap();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public ResponseData(Map data) {
        this.data = data;
    }

    public ResponseData() {
    }

    @Override
    public String toString() {
        return Jackson.toJsonString(this);
    }
}
