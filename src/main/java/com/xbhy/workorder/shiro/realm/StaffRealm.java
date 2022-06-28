package com.xbhy.workorder.shiro.realm;


import com.xbhy.workorder.entity.StaffRole;
import com.xbhy.workorder.enums.RoleKey;
import com.xbhy.workorder.exception.user.*;
import com.xbhy.workorder.service.StaffOrderService;
import com.xbhy.workorder.service.StaffRoleService;
import com.xbhy.workorder.service.SysRoleService;
import com.xbhy.workorder.shiro.service.SysLoginService;
import com.xbhy.workorder.util.ShiroUtils;
import com.xbhy.workorder.vo.StaffVO;
import com.xbhy.workorder.vo.UserPassWordOrVerigicationCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 自定义Realm 处理登录 权限
 * 
 * @author
 */
@Slf4j
public class StaffRealm extends AuthorizingRealm
{
    @Autowired
    private StaffOrderService staffOrderService;

    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private StaffRoleService staffRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0)
    {
        StaffVO staffVO = ShiroUtils.getStaff();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<StaffRole> staffRoles = staffRoleService.queryByStaffId(staffVO.getId());
        List<String> roleKeys = staffRoles.stream().map(StaffRole::getRoleKey).collect(Collectors.toList());
        if(Optional.ofNullable(staffRoles).isPresent() && roleKeys.contains(RoleKey.ADMIN.getKey())){
        // 管理员拥有所有权限
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }
        return info;
    }

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
        catch (UserPasswordRetryLimitExceedException e)
        {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        }
        catch (UserBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
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
}
