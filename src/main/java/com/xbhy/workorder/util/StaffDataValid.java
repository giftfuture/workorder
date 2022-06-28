package com.xbhy.workorder.util;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import com.lancet.common.constant.CommonPool;
import com.lancet.common.exception.UserCenterException;
import com.lancet.common.util.NumberUtils;
import com.lancet.common.util.StringUtils;
import com.lancet.common.vo.api.CommonResult;
import com.lancet.usercenter.common.enums.CodeX;
import com.lancet.usercenter.common.vo.UpdateUserVO;
import com.xbhy.workorder.exception.BizException;
import com.xbhy.workorder.exception.CodeX;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Slf4j
public class StaffDataValid {

    static final String loginNameIndex = "uqi_loginname_idx";
    static final String mobileIndex = "uqi_phone_idx";
    static final String emailIndex = "uqi_email_idx";
    static final String staffnoIndex = "uqi_staffno_idx";

    public static void validIndex(Throwable e){
        Throwable cause = e.getCause();
        if (cause instanceof java.sql.SQLIntegrityConstraintViolationException) {
            String errMsg = cause.getMessage();
            //根据约束名称定位是那个字段
            if(StringUtils.isNotBlank(errMsg)){
                if (errMsg.contains(loginNameIndex)) {
                    log.error(e.getMessage());
                    throw new BizException(C.error(CodeX._60030));
                }
                if (errMsg.contains(mobileIndex) ) {
                    throw new BizException(CommonResult.error(CodeX._60028));
                }
                if (errMsg.contains(emailIndex) ) {
                    throw new BizException(CommonResult.error(CodeX._60029));
                }
                if (errMsg.contains(staffnoIndex)) {
                    throw new BizException(CommonResult.error(CodeX._60049));
                }
            }
        }

    }

    /**
     * 添加用户参数校验
     */
    public static void checkParams(UpdateUserVO updateUserVO) {
        //校验性别（1:男,2:女,3:保密）
//        if(null != updateUserVO.getGender() && (updateUserVO.getGender() < CommEnum.Gender.MALE.getCode() || updateUserVO.getGender() >CommEnum.Gender.SECRET.getCode())){
//            throw new UserCenterException(CommonResult.error(Code._60064));
//        }
//        //校验证件类型(1:身份证,2:港澳台居民身份证,3:护照,4:外国人永久居留身份证)
//        if(null != updateUserVO.getCardType() && (updateUserVO.getCardType() <CommEnum.CardType.IDCARD.getCode() || updateUserVO.getCardType() > CommEnum.CardType.FOREIGNCARD.getCode())){
//            throw new UserCenterException(CommonResult.error(Code._60065));
//        }
//        //校验婚姻状况(1:未婚,2:初婚,3:离婚,4:再婚,5:丧偶)
//        if(null != updateUserVO.getMarriage() && (updateUserVO.getMarriage() < CommEnum.MARRAGE.SIGNAL.getCode() || updateUserVO.getMarriage() > CommEnum.MARRAGE.WIDOWED.getCode())){
//            throw new UserCenterException(CommonResult.error(Code._60066));
//        }
//        //校验教育背景学历(1:高中及以下,2:大专,3:本科,4:硕士,5:博士及以上)
//        if(null != updateUserVO.getEducation() && (updateUserVO.getEducation() < CommEnum.EDUCATION.HIGHMIDDLE.getCode() || updateUserVO.getEducation() > CommEnum.EDUCATION.DOCTOR.getCode())){
//            throw new UserCenterException(CommonResult.error(Code._60067));
//        }

        //手机号格式校验
        if (StringUtils.isNotBlank(updateUserVO.getMobile()) && !PhoneUtil.isMobile(updateUserVO.getMobile())) {
            throw new UserCenterException(CommonResult.error(CodeX._60040));
        }
        //身份证号格式校验
        if (StringUtils.isNotBlank(updateUserVO.getCardNo()) && !IdcardUtil.isValidCard(updateUserVO.getCardNo())) {
            throw new UserCenterException(CommonResult.error(CodeX._60060));
        }
        //固定电话格式校验
        if(StringUtils.isNotBlank(updateUserVO.getFixedPhone()) && !PhoneUtil.isTel(updateUserVO.getFixedPhone())){
            throw new UserCenterException(CommonResult.error(CodeX._60063));
        }
        //身份证有效期合法性校验
        if(StringUtils.isNotBlank(updateUserVO.getCardTerm())) {
            String [] cardTermArray = updateUserVO.getCardTerm().split("-");
            if(cardTermArray.length <2){
                throw new UserCenterException(CommonResult.error(CodeX._60060));
            }
            String startTerm = StringUtils.trim(cardTermArray[0]);
            String endTerm = StringUtils.trim(cardTermArray[1]);
            Date beforeDate ;
            if(!NumberUtils.isNumeric(startTerm)) {
                throw new UserCenterException(CommonResult.error(CodeX._60061));
            }else{
                beforeDate = DateUtils.parseToDate(startTerm, DateStyle.YYYYMMDD.getValue());
            }
            //验证身份证有效期格式
            if(NumberUtils.isNumeric(endTerm)){
                Date afterDate = DateUtils.parseToDate(endTerm, DateStyle.YYYYMMDD.getValue());
                Date date = DateUtils.now();
                if(null != beforeDate &&  afterDate != null && (date.before(beforeDate) || date.after(afterDate))){
                    throw new UserCenterException(CommonResult.error(CodeX._60062));
                }
            }else if(!CommonPool.LONGTERMINATED.equals(endTerm)){
                throw new UserCenterException(CommonResult.error(CodeX._60061));
            }
        }
    }
}
