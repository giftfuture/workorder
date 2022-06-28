package com.xbhy.workorder.util;

import com.xbhy.workorder.entity.Staff;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySecurityUtil {

    private static Logger logger = LoggerFactory.getLogger(MySecurityUtil.class);

    public static Staff getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        Staff staff = (Staff) subject.getPrincipal();
        staff.setPasswd(null);
        return staff;
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUsername() {
        return getCurrentUser().getStaffName();
    }

}
