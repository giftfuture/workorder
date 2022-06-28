package com.xbhy.workorder.exception.user;

import com.xbhy.workorder.config.BaseConfig;

/**
 * 验证码错误异常类
 * 
 * @author
 */
public class CaptchaException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException(){
        super("staff", 1, BaseConfig.jcaptchaError);
    }
}
