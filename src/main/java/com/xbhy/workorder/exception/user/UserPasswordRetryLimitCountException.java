package com.xbhy.workorder.exception.user;

/**
 * 用户错误记数异常类
 * 
 * @author
 */
public class UserPasswordRetryLimitCountException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount)
    {
        super("user.passwd.retry.limit.count", new Object[] { retryLimitCount });
    }
}
