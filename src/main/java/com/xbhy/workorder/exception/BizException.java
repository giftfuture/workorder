package com.xbhy.workorder.exception;

/**
 * @Description:
 * @Author: Athrun
 * @Date: 2019/4/13
 * @Version 1.0
 */
public class BizException extends Exception {

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private Integer code;

    private BizException() {
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}
