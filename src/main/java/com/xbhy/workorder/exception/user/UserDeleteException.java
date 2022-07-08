package com.xbhy.workorder.exception.user;

/**
 * 用户账号已被删除
 * 
 * @author
 */
public class UserDeleteException extends StaffException
{
    private static final long serialVersionUID = 1L;

    public UserDeleteException()
    {
        super(2, null);
    }
}
