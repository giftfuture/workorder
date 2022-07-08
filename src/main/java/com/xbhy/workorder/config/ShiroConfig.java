package com.xbhy.workorder.config;

import com.xbhy.workorder.component.UserAuthRealm;
import com.xbhy.workorder.dao.StaffDao;
import com.xbhy.workorder.dao.SysPermissionDao;
import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.entity.SysPermission;
import com.xbhy.workorder.exception.BizException;
import com.xbhy.workorder.exception.user.UserPasswordNotMatchException;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
public class ShiroConfig {


    @Autowired
    private StaffDao staffDao;

    @Resource
    private UserAuthRealm userAuthRealm;

    @Autowired
    private SysPermissionDao sysPermissionDao;

//    @Override
//    public boolean supports(AuthenticationToken token) {
//        if (token instanceof LoginToken) {
//            return ((LoginToken) token).getLoginType() == LoginType.USER_PASSWORD;
//        } else {
//            return false;
//        }
//    }



    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        Staff staff = staffDao.selectByLoginName(username);
        if (Optional.ofNullable(staff).isPresent()) {
            throw new UnknownAccountException();
        }
        // 加密
        String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        // 使用正确密码验证密码是否正确
        if (!BCrypt.checkpw(password, encodedPassword)) {
            throw new UserPasswordNotMatchException();
        }
        return new SimpleAuthenticationInfo(staff, password, staff.getLoginName());
    }
    /**
     * 配置 资源访问策略 . web应用程序 shiro核心过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean factoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login");//登录页
        factoryBean.setSuccessUrl("/index");//首页
        factoryBean.setUnauthorizedUrl("/unauthorized");//未授权界面;
        factoryBean.setFilterChainDefinitionMap(setFilterChainDefinitionMap()); //配置 拦截过滤器链
        return factoryBean;
    }
    /**
     * 配置 SecurityManager,可配置一个或多个realm
     * @return
     */
    @Bean
    public SecurityManager  securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userAuthRealm);
//        securityManager.setRealm(xxxxRealm);
        return securityManager;
    }
    /**
     * 开启shiro 注解支持. 使以下注解能够生效 :
     * 需要认证 {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}
     * 需要用户 {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}
     * 需要访客 {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}
     * 需要角色 {@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}
     * 需要权限 {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * 配置 拦截过滤器链.  map的键 : 资源地址 ;  map的值 : 所有默认Shiro过滤器实例名
     * 默认Shiro过滤器实例 参考 : {@link org.apache.shiro.web.filter.mgt.DefaultFilter}
     */
    private Map<String, String> setFilterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<>();
        //注册 数据库中所有的权限 及其对应url
        List<SysPermission> allPermission = sysPermissionDao.queryAllByLimit(new SysPermission(), new UnPage());//数据库中查询所有权限
        for (SysPermission sysPermission : allPermission) {
            filterMap.put(sysPermission.getPermUrl(), "perms[" + sysPermission.getPermName() + "]");    //拦截器中注册所有的权限
        }
        filterMap.put("/static/**", "anon");    //公开访问的资源
        filterMap.put("/open/api/**", "anon");  //公开接口地址
        filterMap.put("/logout", "logout");     //配置登出页,shiro已经帮我们实现了跳转
        filterMap.put("/**", "authc");          //所有资源都需要经过验证
        return filterMap;
    }
}
