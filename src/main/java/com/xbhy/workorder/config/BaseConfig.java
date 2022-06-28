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

    public static String attachUrl;

    public static String jcaptchaError;

    @Value("${attachUrl}")
    public static void setAttachUrl(String attachUrl) {
        BaseConfig.attachUrl = attachUrl;
    }
    @Value("${staff.jcaptcha.error}")
    public void setJcaptchaError(String jcaptchaError) {
        BaseConfig.jcaptchaError = jcaptchaError;
    }


}
