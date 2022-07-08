package com.xbhy.workorder.exception.user;

import com.xbhy.workorder.config.BaseConfig;

/**
 * 用户密码不正确或不符合规范异常类
 * 
 * @author
 */
public class UserPasswordNotMatchException extends StaffException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super(4, BaseConfig.getLoginNamePwdNotMatch());
    }
}
