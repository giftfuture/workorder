package com.xbhy.workorder.shiro.realm;


import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.entity.StaffRole;
import com.xbhy.workorder.entity.SysPermission;
import com.xbhy.workorder.enums.RoleKey;
import com.xbhy.workorder.exception.user.*;
import com.xbhy.workorder.service.*;
import com.xbhy.workorder.shiro.service.SysLoginService;
import com.xbhy.workorder.util.ShiroUtils;
import com.xbhy.workorder.vo.StaffRoleVO;
import com.xbhy.workorder.vo.StaffVO;
import com.xbhy.workorder.vo.SysPermissionVO;
import com.xbhy.workorder.vo.UserPassWordOrVerigicationCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 自定义Realm 处理登录 权限
 * 
 * @author
 */
@Slf4j
@Component
@Configuration
public class StaffAuthRealm extends AuthorizingRealm {

    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffOrderService staffOrderService;
    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private StaffRoleService staffRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;


    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0)
    {
        StaffVO staffVO = ShiroUtils.getStaff();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<StaffRoleVO> staffRoles = staffRoleService.queryByStaffId(staffVO.getId());
        List<String> roleKeys = staffRoles.stream().map(StaffRoleVO::getRoleKey).collect(Collectors.toList());
        if(Optional.ofNullable(staffRoles).isPresent() && roleKeys.contains(RoleKey.ADMIN.getKey())){
        // 管理员拥有所有权限
            authorizationInfo.addRole("admin");
            //authorizationInfo.addRole(role.getRoleKey());                      //添加 角色
            for (StaffRoleVO role : staffRoles) {
                List<SysPermissionVO> sysPermissionVOS = sysPermissionService.queryByRoleId(role.getRoleId());
                for (SysPermissionVO permission : sysPermissionVOS) {           //获取 权限
                    authorizationInfo.addStringPermission(permission.getPermName());//添加 权限
                }
            }
        }
        return authorizationInfo;
    }
    /**
     * 用户登陆 凭证信息
     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        String loginName = (String) token.getPrincipal();
//        Staff staff = staffDao.selectByLoginName(loginName);
//        if (staff == null) return null;
//        String credentials = staff.getPasswd() + staff.getLoginName() + staff.getPasswd();//自定义加盐 salt + username + salt
//        return new SimpleAuthenticationInfo(
//                staff.getLoginName(), //用户名
//                staff.getPasswd(), //密码
//                ByteSource.Util.bytes(credentials), //加密
//                getName()  //realm name
//        );
//    }
    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        UserPassWordOrVerigicationCode upToken = (UserPassWordOrVerigicationCode) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null)
        {
            password = new String(upToken.getPassword());
        }

        StaffVO user = null;
        try
        {
            user = sysLoginService.login(username, password,upToken.getVerificationCode());
        }
        catch (CaptchaException e)
        {
            throw new AuthenticationException(e.getMessage(), e);
        }
        catch (UserNotExistsException e)
        {
            throw new UnknownAccountException(e.getMessage(), e);
        }
        catch (UserPasswordNotMatchException e)
        {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        }
        catch (UserPasswordRetryLimitCountException e)
        {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        }
        catch (UseVerificationcodeNotMatchException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 设置 realm的 HashedCredentialsMatcher
     */
    @PostConstruct
    public void setHashedCredentialsMatcher() {
        this.setCredentialsMatcher(hashedCredentialsMatcher());
    }
    /**
     * 凭证匹配 : 指定 加密算法,散列次数
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }
}
