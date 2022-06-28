package com.xbhy.workorder.exception;


import org.apache.shiro.authc.AccountException;

public class WaitCheckException extends AccountException {

    public WaitCheckException() {

    }

    public WaitCheckException(String message) {
        super(message);
    }

    public WaitCheckException(Throwable cause) {
        super(cause);
    }

    public WaitCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
