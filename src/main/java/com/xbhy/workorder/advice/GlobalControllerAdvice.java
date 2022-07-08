package com.xbhy.workorder.advice;


import com.alibaba.fastjson.JSON;
import com.xbhy.workorder.util.DateUtils;
import com.xbhy.workorder.util.IpUtils;
import com.xbhy.workorder.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stephen
 * Company:
 * Createtime :
 * Description : rest full 全局统一返回封装
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.StringToDate(text));
            }
        });
    }
    /**
     * 判断哪些需要拦截
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //如果返回的数据是ResultObjectModel、Byte、String类型则不进行封装
        String path = ((ServletServerHttpRequest) request).getServletRequest().getServletPath();
        if (path.contains("swagger-resources") || path.contains("v2/api-docs")){
            return body;
        }
        if( body instanceof ResponseVO || body instanceof ResponseEntity || body instanceof Byte || body instanceof String) {
            return body;
        }
        return this.getWrapperResponse(request , body);
    }

    /**
     * 返回正常的信息
     * @param request
     * @param data
     * @return
     */
    private ResponseVO<Object> getWrapperResponse(ServerHttpRequest request, Object data) {
        return new ResponseVO<Object>(0, "请求成功" , data);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity throwable(Throwable throwable, HttpServletRequest request) throws ParseException {
        Map<String, String> resultMap = getThrowable(throwable);
        if (request != null) {
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            resultMap.put("Requester-Ip", IpUtils.getIpAddress(request));
            resultMap.put("Requester-Agent", request.getHeader("user-agent"));
            if (statusCode != null) {
                new ResponseEntity<>(JSON.toJSON(resultMap).toString(), HttpStatus.valueOf(statusCode));
            }
        }
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity authenticationException(AuthenticationException serverError) throws ParseException {
        Map<String, String> resultMap = getThrowable(serverError);
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = HttpStatusCodeException.class)
    public ResponseEntity httpStatusCodeException(HttpStatusCodeException serverError) throws ParseException {
        Map<String, String> resultMap = getThrowable(serverError);
        HttpStatus status = serverError.getStatusCode();
        resultMap.put("responseBody", "" + serverError.getResponseBodyAsString());
        resultMap.put("statusCode", "" + status.toString());
        resultMap.put("statusText", "" + serverError.getStatusText());
        resultMap.put("statusReasonPhrase", "" + status.getReasonPhrase());
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), status);
    }

    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public ResponseEntity incorrectCredentialsException(IncorrectCredentialsException i) throws ParseException {
        Map<String, String> resultMap = getThrowable(i);
        resultMap.put("message", "用户名/密码错误,认证失败");
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity unauthorizedException(UnauthorizedException i) throws ParseException {
        Map<String, String> resultMap = getThrowable(i);
        resultMap.put("message", "用户无角色(权限)访问此接口");
        return new ResponseEntity<>(JSON.toJSON(resultMap).toString(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 公共异常信息
     */
    private Map<String, String> getThrowable(Throwable throwable) throws ParseException {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("throwable", "" + throwable);
        resultMap.put("throwableTime", "" + DateUtils.nowTime());
        resultMap.put("message", "" + throwable.getMessage());
        resultMap.put("localizedMessage", "" + throwable.getLocalizedMessage());
        log.error("Exception : {}", JSON.toJSON(resultMap));
        log.error("printStackTrace", throwable);
        return resultMap;
    }
}