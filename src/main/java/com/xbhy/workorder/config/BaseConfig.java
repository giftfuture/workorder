package com.xbhy.workorder.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 基础配置
 */
@Configuration
@Data
@Component
@Slf4j
public class BaseConfig {

    private static String attachUrl;

    private static String jcaptchaError;

    private static String historyFilePath;

    private static String loginNameIndex ;

    private static String mobileIndex ;

    private static String emailIndex ;

    private static String staffNoIndex ;

    private static String  updatePwdSuccess;

    private static String updatePwdFailed;

    private static String confirmEqualPwd;

    private static String notEqualOldPwd;

    private static String updateStaffFailed;

    private static String loginNameNoExist;

    private static String resetPwdFailed;

    private static String resetPwdSuccess;

    private static String loginNamePwdNotMatch;

    private static String retryLimitCount;

    @Value("${attachUrl}")
    private void setAttachUrl(String attachUrl) {
        BaseConfig.attachUrl = attachUrl;
    }

    @Value("${staff.jcaptcha.error}")
    private void setJcaptchaError(String jcaptchaError) {
        BaseConfig.jcaptchaError = jcaptchaError;
    }
    @Value("${record.file.path}")
    private void setHistoryFilePath(String historyFilePath) {
        BaseConfig.historyFilePath = historyFilePath;
    }

    @Value("${staff.loginNameIndex}")
    public void setLoginNameIndex(String loginNameIndex) {
        BaseConfig.loginNameIndex = loginNameIndex;
    }
    @Value("${staff.mobileIndex}")
    public void setMobileIndex(String mobileIndex) {
        BaseConfig.mobileIndex = mobileIndex;
    }
    @Value("${staff.emailIndex}")
    public void setEmailIndex(String emailIndex) {
        BaseConfig.emailIndex = emailIndex;
    }
    @Value("${staff.staffNoIndex}")
    public void setStaffNoIndex(String staffNoIndex) {
        BaseConfig.staffNoIndex = staffNoIndex;
    }
    @Value("${staff.updatePwdSuccess}")
    public void setUpdatePwdSuccess(String updatePwdSuccess) {
        BaseConfig.updatePwdSuccess = updatePwdSuccess;
    }
    @Value("${staff.updatePwdFailed}")
    public void setUpdatePwdFailed(String updatePwdFailed) {
        BaseConfig.updatePwdFailed = updatePwdFailed;
    }
    @Value("${staff.confirmEqualPwd}")
    public void setConfirmEqualPwd(String confirmEqualPwd) {
        BaseConfig.confirmEqualPwd = confirmEqualPwd;
    }
    @Value("${staff.notEqualOldPwd}")
    public void setNotEqualOldPwd(String notEqualOldPwd) {
        BaseConfig.notEqualOldPwd = notEqualOldPwd;
    }

    @Value("${staff.updateStaffFailed}")
    public void setUpdateStaffFailed(String updateStaffFailed) {
        BaseConfig.updateStaffFailed = updateStaffFailed;
    }
    @Value("${staff.loginNameNoExist}")
    public void setLoginNameNoExist(String loginNameNoExist) {
        BaseConfig.loginNameNoExist = loginNameNoExist;
    }
    @Value("${staff.resetPwdFailed}")
    public void setResetPwdFailed(String resetPwdFailed) {
        BaseConfig.resetPwdFailed = resetPwdFailed;
    }
    @Value("${staff.resetPwdSuccess}")
    public void setResetPwdSuccess(String resetPwdSuccess) {
        BaseConfig.resetPwdSuccess = resetPwdSuccess;
    }
    @Value("${staff.loginNamePwdNotMatch}")
    public void setLoginNamePwdNotMatch(String loginNamePwdNotMatch) {
        BaseConfig.loginNamePwdNotMatch = loginNamePwdNotMatch;
    }
    @Value("${staff.retryLimitCount}")
    public void setRetryLimitCount(String retryLimitCount) {
        BaseConfig.retryLimitCount = retryLimitCount;
    }

    public static String getRetryLimitCount() {
        return retryLimitCount;
    }

    public static String getLoginNamePwdNotMatch() {
        return loginNamePwdNotMatch;
    }

    public static String getAttachUrl() {
        return attachUrl;
    }

    public static String getJcaptchaError() {
        return jcaptchaError;
    }

    public static String getHistoryFilePath() {
        return historyFilePath;
    }

    public static String getLoginNameIndex() {
        return loginNameIndex;
    }

    public static String getMobileIndex() {
        return mobileIndex;
    }

    public static String getEmailIndex() {
        return emailIndex;
    }

    public static String getStaffNoIndex() {
        return staffNoIndex;
    }

    public static String getUpdatePwdSuccess() {
        return updatePwdSuccess;
    }

    public static String getUpdatePwdFailed() {
        return updatePwdFailed;
    }

    public static String getConfirmEqualPwd() {
        return confirmEqualPwd;
    }

    public static String getNotEqualOldPwd() {
        return notEqualOldPwd;
    }

    public static String getUpdateStaffFailed() {
        return updateStaffFailed;
    }

    public static String getLoginNameNoExist() {
        return loginNameNoExist;
    }

    public static String getResetPwdFailed() {
        return resetPwdFailed;
    }

    public static String getResetPwdSuccess() {
        return resetPwdSuccess;
    }
}
