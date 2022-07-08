package com.xbhy.workorder.exception.user;

import com.xbhy.workorder.config.BaseConfig;

/**
 * 用户不存在异常类
 * 
 * @author
 */
public class UserNotExistsException extends StaffException
{
    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super(3, BaseConfig.getLoginNameNoExist());
    }
}
