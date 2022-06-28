package com.xbhy.workorder.vo;

/**
 * Create by 孙斌 on 2019/7/9.
 */
public enum ResultCode {
    SUCCESS(200 , "请求成功"),
    ERROR(500 , "请求失败");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultCode() {

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
