package com.xbhy.workorder.component;

import com.xbhy.workorder.dao.StaffDao;
import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.entity.SysPermission;
import com.xbhy.workorder.entity.SysRole;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
@Component
public class UserAuthRealm extends AuthorizingRealm {

    @Autowired
    private StaffDao staffDao;
    /**
     * 权限核心配置 根据数据库中的该用户 角色 和 权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Staff user = (Staff) principals.getPrimaryPrincipal();
        for (SysRole role : user.getRoleList()) {                                 //获取 角色
            authorizationInfo.addRole(role.getRoleKey());                      //添加 角色
            for (SysPermission permission : role.getSysPermissionList()) {           //获取 权限
                authorizationInfo.addStringPermission(permission.getPermName());//添加 权限
            }
        }
        return authorizationInfo;
    }
    /**
     * 用户登陆 凭证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginName = (String) token.getPrincipal();
        Staff staff = staffDao.selectByLoginName(loginName);
        if (staff == null) return null;
        String credentials = staff.getPasswd() + staff.getLoginName() + staff.getPasswd();//自定义加盐 salt + username + salt
        return new SimpleAuthenticationInfo(
                staff.getLoginName(), //用户名
                staff.getPasswd(), //密码
                ByteSource.Util.bytes(credentials), //加密
                getName()  //realm name
        );
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