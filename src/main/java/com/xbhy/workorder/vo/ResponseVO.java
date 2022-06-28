package com.xbhy.workorder.vo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author stephen
 * Company:
 * Createtime :
 * Description : rest full 统一返回包装类
 */
@Setter
@Getter
public class ResponseVO<T> implements Serializable {

    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据内容
     */
    private T data;

    public ResponseVO() {
    }

    public ResponseVO(Integer code, String message, T data) {
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
    public static <T> ResponseVO success(T data){
        return new ResponseVO(0 , "请求成功" , data);
    }

    /**
     * 返回成功
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseVO success(String message ,T data){
        return new ResponseVO(0 , message , data);
    }

    /**
     * 失败
     * @param message
     * @return
     */
    public static ResponseVO fail(String message){
        return new ResponseVO(1 , message , null);
    }

    /**
     * 失败
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseVO fail(String message ,T data){
        return new ResponseVO(1 , message , data);
    }
}
