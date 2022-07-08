package com.xbhy.workorder.exception.user;

import com.xbhy.workorder.config.BaseConfig;

/**
 * 验证码错误
 * 
 * @author
 */
public class UseVerificationcodeNotMatchException extends StaffException
{
    private static final long serialVersionUID = 1L;

    public UseVerificationcodeNotMatchException()
    {
        super(6, BaseConfig.getJcaptchaError());
    }
}
