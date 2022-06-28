package com.xbhy.workorder.util.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpException;

import java.io.IOException;

/**
 *
 */
public class HttpClientHelper {

    public static String sendPostJson(String urlParam, Object object) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
        // 创建post请求方法实例对象
        PostMethod PostMethod = new PostMethod(urlParam);
        // 设置post请求超时时间
        PostMethod.setRequestEntity(new StringRequestEntity(new String(object.toString().getBytes(),"utf-8")));
        PostMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        //设置xml还是json
        PostMethod.setRequestHeader("Content-Type", "application/json; charset=utf-8");
        httpClient.executeMethod(PostMethod);
        String result = new String(PostMethod.getResponseBody());
        PostMethod.releaseConnection();
        return result;
    }

}
