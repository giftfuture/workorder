package com.xbhy.workorder.exception;

import org.apache.shiro.authc.AccountException;

public class UnpassException extends AccountException {

    public UnpassException() {

    }

    public UnpassException(String message) {
        super(message);
    }

    public UnpassException(Throwable cause) {
        super(cause);
    }

    public UnpassException(String message, Throwable cause) {
        super(message, cause);
    }
}

