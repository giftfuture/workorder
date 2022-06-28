package com.xbhy.workorder.vo;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
 */
public class UserPassWordOrVerigicationCode extends UsernamePasswordToken {

    //验证码
    private String verificationCode;

    private Boolean rememberMe;

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public UserPassWordOrVerigicationCode(){}

    public UserPassWordOrVerigicationCode(String username,String password,String verificationCode,Boolean rememberMe){
        super(username,password,rememberMe);
        this.verificationCode = verificationCode;
    }
}
