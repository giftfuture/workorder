package com.xbhy.workorder.exception;

import org.apache.shiro.authc.AccountException;

/**
 * @Description:
 * @Author:
 * @Date:
 * @Version: 1.0.0
 */
public class InvalidAccountException extends AccountException {

    public InvalidAccountException() {

    }

    public InvalidAccountException(String message) {
        super(message);
    }

    public InvalidAccountException(Throwable cause) {
        super(cause);
    }

    public InvalidAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
