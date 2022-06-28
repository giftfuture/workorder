package com.xbhy.workorder.shiro.web.filter.pre;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ycp.web.config.PhConfiguration;
import com.ycp.web.exception.BusinessException;
import com.ycp.web.utils.StreamUtil;
import com.ycp.web.utils.security.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Created by admin on 2018/11/12.
 */
@Component
@Order(100)
public class RequestPrefixFilter implements Filter {
    private static Logger log = LoggerFactory.getLogger(RequestPrefixFilter.class);

    @Autowired
    private PhConfiguration phConfiguration;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletrequest;
        HttpServletResponse response = (HttpServletResponse) servletresponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With,app-type-version");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Location");
        response.setContentType("application/json;charset=UTF-8");


        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        ServletInputStream inputStream = requestWrapper.getInputStream();
        String stringStr = StreamUtil.getStreamString(inputStream);
        request = requestWrapper;
        log.debug("pc项目收到请求:{method:} " + request.getMethod() + "{url:}" + request.getRequestURI() + "{request:}" + stringStr + "            {contentType：}" + request.getContentType());
        String streamString = stringStr;
        if (!streamString.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonRequestMap = objectMapper.readValue(streamString, Map.class);
            String source = (String) jsonRequestMap.get("source");
            String timeStamp = (String) jsonRequestMap.get("timeStamp");
            String pcToken = (String) jsonRequestMap.get("token");
            String methodName = (String) jsonRequestMap.get("methodName");
            Map<String, Object> parameters = (Map<String, Object>) jsonRequestMap.get("parameter");
            if (source != null && source.equals("10")) {
                //判断加密
                //token=  MD5(appid  +  MD5 (time  + UpperCase(methodName)) + appid)
                String contrast = MD5Util.getMD5(phConfiguration.getAppIdPhhyy() + MD5Util.getMD5(timeStamp + methodName.toUpperCase()) + phConfiguration.getAppIdPhhyy());
                if (!pcToken.equals(contrast)) {
                    log.debug("token有问题");
                    throw new BusinessException("token异常！");
                }
                BasicBodyRequestWrapper wrapper = new BasicBodyRequestWrapper(request, parameters);
                request = wrapper;

            }
        }

        filterchain.doFilter(request, response);

    }


    public class BasicBodyRequestWrapper extends HttpServletRequestWrapper {

        private Map<String, Object> params;
        private byte[] contentData;

        public BasicBodyRequestWrapper(HttpServletRequest request, Map<String, Object> newParams) {
            super(request);
            this.params = newParams;
            renewParameterMap(request);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String json = objectMapper.writeValueAsString(params);
                contentData = json.getBytes("UTF-8");
            } catch (JsonProcessingException e) {
                contentData = new byte[0];
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                contentData = new byte[0];
                e.printStackTrace();
            }
        }

        @Override
        public long getContentLengthLong() {
            return contentData.length;
        }

        @Override
        public String getParameter(String name) {
            String result = "";

            Object v = params.get(name);
            if (v == null) {
                result = null;
            } else if (v instanceof String[]) {
                String[] strArr = (String[]) v;
                if (strArr.length > 0) {
                    result = strArr[0];
                } else {
                    result = null;
                }
            } else if (v instanceof String) {
                result = (String) v;
            } else {
                result = v.toString();
            }

            return result;
        }

        @Override
        public Map getParameterMap() {
            return params;
        }

        @Override
        public Enumeration getParameterNames() {
            return new Vector(params.keySet()).elements();
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] result = null;

            Object v = params.get(name);
            if (v == null) {
                result = null;
            } else if (v instanceof String[]) {
                result = (String[]) v;
            } else if (v instanceof String) {
                result = new String[]{(String) v};
            } else {
                result = new String[]{v.toString()};
            }

            return result;
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (null != name && (name.equals("Content-Type") || name.equals("content-type") || name.equals("contenttype"))) {
                return new Enumeration<String>() {
                    private boolean hasGetted = false;

                    @Override
                    public String nextElement() {
                        if (hasGetted) {
                            throw new NoSuchElementException();
                        } else {
                            hasGetted = true;
                            return "application/json;charset=utf-8";
                        }
                    }

                    @Override
                    public boolean hasMoreElements() {
                        return !hasGetted;
                    }
                };
            }
            return super.getHeaders(name);
        }

        private void renewParameterMap(HttpServletRequest req) {

            String queryString = req.getQueryString();

            if (queryString != null && queryString.trim().length() > 0) {
                String[] params = queryString.split("&");

                for (int i = 0; i < params.length; i++) {
                    int splitIndex = params[i].indexOf("=");
                    if (splitIndex == -1) {
                        continue;
                    }

                    String key = params[i].substring(0, splitIndex);

                    if (!this.params.containsKey(key)) {
                        if (splitIndex < params[i].length()) {
                            String value = params[i].substring(splitIndex + 1);
                            this.params.put(key, new String[]{value});
                        }
                    }
                }
            }
        }
    }

}
