package com.xbhy.workorder.exception.user;


import com.xbhy.workorder.exception.BaseException;

/**
 * 用户信息异常类
 * 
 * @author
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;
    public UserException(Integer code,String msg)
    {
        super("staff", code, msg);
    }
    public UserException(Integer code, Object[] args,String msg)
    {
        super("staff", code, args, msg);
    }
}
