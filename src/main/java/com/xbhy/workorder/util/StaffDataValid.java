package com.xbhy.workorder.util;

import com.xbhy.workorder.config.BaseConfig;
import com.xbhy.workorder.exception.BizException;
import com.xbhy.workorder.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Slf4j
public class StaffDataValid {

    static final String loginNameIndex = "uqi_loginname_idx";
    static final String mobileIndex = "uqi_phone_idx";
    static final String emailIndex = "uqi_email_idx";
    static final String staffNoIndex = "uqi_staffno_idx";

    public static void validIndex(Throwable e){
        Throwable cause = e.getCause();
        if (cause instanceof java.sql.SQLIntegrityConstraintViolationException) {
            String errMsg = cause.getMessage();
            //根据约束名称定位是那个字段
            if(StringUtils.isNotBlank(errMsg)){
                if (errMsg.contains(loginNameIndex)) {
                    log.error(e.getMessage());
                    throw new BizException(ResponseVO.fail(BaseConfig.getLoginNameIndex()));
                }
                if (errMsg.contains(mobileIndex) ) {
                    throw new BizException(ResponseVO.fail(BaseConfig.getMobileIndex()));
                }
                if (errMsg.contains(emailIndex) ) {
                    throw new BizException(ResponseVO.fail(BaseConfig.getEmailIndex()));
                }
                if (errMsg.contains(staffNoIndex)) {
                    throw new BizException(ResponseVO.fail(BaseConfig.getStaffNoIndex()));
                }
            }
        }

    }

    /**
     * 添加用户参数校验
     */
   // public static void checkParams(UpdateUserVO updateUserVO) {
        //校验性别（1:男,2:女,3:保密）
//        if(null != updateUserVO.getGender() && (updateUserVO.getGender() < CommEnum.Gender.MALE.getCode() || updateUserVO.getGender() >CommEnum.Gender.SECRET.getCode())){
//            throw new UserCenterException(CommonResult.error(Code._60064));
//        }
        //手机号格式校验
//        if (StringUtils.isNotBlank(updateUserVO.getMobile()) && !PhoneUtil.isMobile(updateUserVO.getMobile())) {
//            throw new UserCenterException(CommonResult.error(CodeX._60040));
//        }


   // }
}
