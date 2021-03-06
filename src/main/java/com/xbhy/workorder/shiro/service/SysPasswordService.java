package com.xbhy.workorder.shiro.service;


import com.xbhy.workorder.constant.ShiroConstants;
import com.xbhy.workorder.exception.user.UseVerificationcodeNotMatchException;
import com.xbhy.workorder.exception.user.UserPasswordNotMatchException;
import com.xbhy.workorder.exception.user.UserPasswordRetryLimitCountException;
import com.xbhy.workorder.vo.StaffVO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录密码方法
 *
 * @author
 */
@Component
public class SysPasswordService {

    @Autowired
    private CacheManager cacheManager;


    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${staff.maxRetryCount}")
    private String maxRetryCount;

//    @PostConstruct
//    public void init() {
//        System.out.println(loginRecordCache);
//    }

    public void validate(StaffVO user, String password, String verificationCode) {
        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
        String loginName = user.getLoginName();
        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue()) {
            throw new UserPasswordRetryLimitCountException(Integer.valueOf(maxRetryCount).intValue());
        }
        //如果用验证码登录
        if (StringUtils.isNotBlank(verificationCode)) {
            if(this.loginVerificationCode(user.getPhone(),verificationCode,user.getLoginName(),retryCount)){
                //删除掉缓存用户的错误次数及过期时间
                clearLoginRecordCache(loginName);
            }else{
                //密码错误
                loginRecordCache.put(loginName, retryCount);
                throw new UseVerificationcodeNotMatchException();
            }
        }else {
            //用密码登录
            if (!matches(user, password)) {
                //密码错误
                loginRecordCache.put(loginName, retryCount);
                throw new UserPasswordNotMatchException();
            } else {
                //删除掉缓存用户的错误次数及过期时间
                clearLoginRecordCache(loginName);
            }
        }
    }



    public boolean matches(StaffVO staffVO, String newPassword) {
        //解密
        return BCrypt.checkpw(newPassword, staffVO.getPasswd());
    }

    public void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String password) {
        //这里改用bcrypt加密（加盐加密）每次加密的时候首先会生成一个随机数就是盐，之后将这个随机数与密码进行hash
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /** 验证码登录的判断
     *
     * @param phone                 手机号
     * @param verificationCode      验证码
     * @param loginName             登录的账号
     * @param retryCount            列表
     * @return
     */
    private Boolean loginVerificationCode(String phone,String verificationCode,String loginName,AtomicInteger retryCount){
        if(StringUtils.isNotBlank(phone)){
            throw new UserPasswordNotMatchException();
        }
        String redisVerificationCode = null;//redisDao.getUsersPhone(phone);
        if (StringUtils.isNotBlank(redisVerificationCode)) {
            throw new UseVerificationcodeNotMatchException();
        }
        if (!redisVerificationCode.equals(verificationCode)) {
            throw new UseVerificationcodeNotMatchException();
        }
        return true;
    }


}
