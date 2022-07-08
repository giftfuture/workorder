package com.xbhy.workorder.exception.user;

import com.xbhy.workorder.config.BaseConfig;

/**
 * 验证码错误异常类
 * 
 * @author
 */
public class CaptchaException extends StaffException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException(){
        super(1, BaseConfig.getJcaptchaError());
    }
}
