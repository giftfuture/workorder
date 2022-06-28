package com.xbhy.workorder.exception.user;

/**
 * 用户账号已被删除
 * 
 * @author
 */
public class UserDeleteException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserDeleteException()
    {
        super("user.passwd.delete", null);
    }
}
