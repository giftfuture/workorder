package com.xbhy.workorder.advice;


import com.xbhy.workorder.util.DateUtils;
import com.xbhy.workorder.vo.Response;
import com.xbhy.workorder.vo.ResponseVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @author stephen
 * Company:
 * Createtime :
 * Description : rest full 全局统一返回封装
 */
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
                setValue(DateUtils.parseDate(text));
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
        if( body instanceof ResponseVO || body instanceof Response || body instanceof Byte || body instanceof String) {
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
}