package com.xbhy.workorder.util;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author stephen
 * Company: yueworld
 * Createtime : 2020/8/28 22:11
 * Description : rest full 统一返回包装类
 */
@Setter
@Getter
public class ResponseResult<T> implements Serializable {

    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据内容
     */
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(T data){
        return new ResponseResult(0 , "请求成功" , data);
    }

    /**
     * 返回成功
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(String message ,T data){
        return new ResponseResult(0 , message , data);
    }

    /**
     * 失败
     * @param message
     * @return
     */
    public static ResponseResult fail(String message){
        return new ResponseResult(1 , message , null);
    }

    /**
     * 失败
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(String message ,T data){
        return new ResponseResult(1 , message , data);
    }
}
