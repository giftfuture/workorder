package com.xbhy.workorder.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class IpUtils {



    /**
     * 获取用户IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String[] ipHeaders = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip = request.getRemoteAddr();
        for (String header : ipHeaders) {
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                break;
            }
            ip = request.getHeader(header);
        }
        return ip;
    }
}
