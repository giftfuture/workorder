package com.xbhy.workorder.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 * 
 * @author
 */
public class UseVerificationcodeNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UseVerificationcodeNotMatchException()
    {
        super("user.verificationCode.not.match", null);
    }
}
