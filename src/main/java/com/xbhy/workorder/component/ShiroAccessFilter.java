package com.xbhy.workorder.component;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @Description:
 * @Author: Athrun
 * @Date: 2019/3/20
 * @Version 1.0
 */
public class ShiroAccessFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("{\"errno\": 501, \"errmsg\": \"未登陆或没有权限，请重新登陆并重试.\"}");
            out.flush();
            out.close();
            return false;
        }
    }
}
