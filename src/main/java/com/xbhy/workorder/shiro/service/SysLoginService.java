package com.xbhy.workorder.shiro.service;


import com.xbhy.workorder.constant.ShiroConstants;
import com.xbhy.workorder.constant.UserConstant;
import com.xbhy.workorder.exception.user.*;
import com.xbhy.workorder.service.StaffService;
import com.xbhy.workorder.util.*;
import com.xbhy.workorder.vo.StaffVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 登录校验方法
 *
 * @author
 */
@Component
public class SysLoginService {

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private StaffService staffService;


    /**
     * 登录
     */
    public StaffVO login(String usrName, String paswd, String verificationCode) {
        //验证码校验   目前不需要验证认证
        if (Optional.ofNullable(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)).isEmpty()) {
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isAnyEmpty(usrName, paswd) ) {
            throw new UserNotExistsException();
        }
        //如果验证码不为空 那么判断验证码
        if(StringUtils.isNotBlank(verificationCode)){
            if(verificationCode.length() != UserConstant.VERIFICATIONCODE_LENGTH){
                throw new UseVerificationcodeNotMatchException();
            }
        }else {
            // 密码如果不在指定范围内 错误
            if (paswd.length() < UserConstant.PASSWORD_MIN_LENGTH || paswd.length() > UserConstant.PASSWORD_MAX_LENGTH) {
                throw new UserPasswordNotMatchException();
            }
        }

        // 用户名不在指定范围内 错误
        if (usrName.length() < UserConstant.USERNAME_MIN_LENGTH
                || usrName.length() > UserConstant.USERNAME_MAX_LENGTH) {
            throw new UserPasswordNotMatchException();
        }
        // 查询用户信息
        StaffVO staffVO = staffService.selectByLoginName(usrName);
//        if (user == null && maybeMobilePhoneNumber(username)) {
//            user = staffService.selectUserByPhoneNumber(username);
//        }
//
//        if (user == null && maybeEmail(username)) {
//            user = staffService.selectUserByEmail(username);
//        }

        if (staffVO == null) {
            throw new UserNotExistsException();
        }

        if (staffVO.getDeleted()) {
            throw new UserDeleteException();
        }
        passwordService.validate(staffVO, paswd,verificationCode);
        ShiroUtils.setStaff(staffVO);
        recordLoginInfo(staffVO);
        return staffVO;
    }

    private boolean maybeEmail(String username) {
        if (!username.matches(UserConstant.EMAIL_PATTERN)) {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username) {
        if (!username.matches(UserConstant.MOBILE_PHONE_NUMBER_PATTERN)) {
            return false;
        }
        return true;
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(StaffVO staffVO) {
        staffVO.setLoginIp(ShiroUtils.getIp());
        staffVO.setLoginTime(DateUtils.getNowDate());
        staffService.update(staffVO);
    }
}
