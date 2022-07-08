package com.xbhy.workorder.exception.user;


import com.xbhy.workorder.exception.BaseException;

/**
 * 用户信息异常类
 * 
 * @author
 */
public class StaffException extends BaseException
{
    private static final long serialVersionUID = 1L;
    public StaffException(Integer code,String msg)
    {
        super("staff", code, msg);
    }
    public StaffException(Integer code, Object[] args,String msg)
    {
        super("staff", code, args, msg);
    }
}
