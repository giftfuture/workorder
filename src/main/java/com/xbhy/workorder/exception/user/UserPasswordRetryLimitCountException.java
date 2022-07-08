package com.xbhy.workorder.exception.user;

import com.xbhy.workorder.config.BaseConfig;

/**
 * 用户错误最大次数异常类
 * 
 * @author
 */
public class UserPasswordRetryLimitCountException extends StaffException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount)
    {
        super(5, BaseConfig.getRetryLimitCount());
    }
}
