package com.xbhy.workorder.component;



import com.xbhy.workorder.dao.StaffDao;
import com.xbhy.workorder.entity.Staff;
import com.xbhy.workorder.exception.InvalidAccountException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Slf4j
public class UserPasswordRealm extends UserAuthRealm {


    @Autowired
    private AuthQueryService authQueryService;

    @Autowired
    private StaffDao staffDao;

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof LoginToken) {
            return ((LoginToken) token).getLoginType() == LoginType.USER_PASSWORD;
        } else {
            return false;
        }
    }

    @Override
    public void setAuthorizationCacheName(String authorizationCacheName) {
        super.setAuthorizationCacheName(authorizationCacheName);
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        List<Staff> staffList = authQueryService.queryUsername(username);
        if (staffList.size() == 0) {
            throw new UnknownAccountException("用户未注册");
        }
        Staff staff = staffList.get(0);
        /*
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            throw new InvalidAccountException("用户名或密码错误");
        }
        */
        String status = staff.getStatus();
        if (!password.equals(staff.getPasswd())) {
            throw new InvalidAccountException("用户名或密码错误");
        }
        return new SimpleAuthenticationInfo(staff, password, staff.getLoginName());
    }

}
