package com.xbhy.workorder.exception;

/**
 * @description:
 * @author:
 * @time:
 */
public class TypeCastException extends BaseException {

    public TypeCastException() {
        super();
    }

    public TypeCastException(Integer code) {
        super(code);
    }

    public TypeCastException(String message) {
        super(message);
    }

    public TypeCastException(Integer code, String message) {
        super(code, message);
    }

    public TypeCastException(Integer code, String msg, Throwable throwable) {
        super(code, msg, throwable);
    }


    /**
     * 生成异常
     *
     * @return LogicException
     */
    public static TypeCastException generateException() {
        return new TypeCastException();
    }

    public static TypeCastException generateException(int code) {
        return new TypeCastException(code);
    }

    public static TypeCastException generateException(String msg) {
        return new TypeCastException(msg);
    }

    public static TypeCastException generateException(int code, String msg) {
        return new TypeCastException(code, msg);
    }

    public static TypeCastException generateException(int code, String msg, Throwable throwable) {
        return new TypeCastException(code, msg, throwable);
    }

}
