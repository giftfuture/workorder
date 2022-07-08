package com.xbhy.workorder.exception;

import com.xbhy.workorder.vo.ResponseVO;

/**
 * @Description:
 * @Author:
 * @Date:
 * @Version 1.0
 */
public class BizException extends RuntimeException {

    private ResponseVO result;

    public <T> ResponseVO<T> getResult() {
        return result;
    }

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
    public BizException(ResponseVO<?> result) {
        super("{" + result.getCode() + " : " + result.getMessage() + "}");
        this.result = result;
    }
    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}
