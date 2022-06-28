package com.xbhy.workorder.exception.user;

/**
 * 用户错误最大次数异常类
 * 
 * @author
 */
public class UserPasswordRetryLimitExceedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount)
    {
        super("user.passwd.retry.limit.exceed", new Object[] { retryLimitCount });
    }
}
