package com.xbhy.workorder.vo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author stephen
 * Company:
 * Createtime :
 * Description : rest full 统一返回包装类
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public ResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    /**
     * 返回成功
     * @param <T>
     * @return
     */
    public static <T> ResponseVO success(String msg) {
        return new ResponseVO(0, msg);
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
    public static<T> ResponseVO fail(String message){
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
    /**
     * 失败
     * @param message
     * @param code
     * @param <T>
     * @return
     */
    public static <T> ResponseVO fail(Integer code, String message){
        return new ResponseVO(code , message );
    }

    public static Object badArgument() {
        return fail(401, "参数不对");
    }

    public static Object badArgumentValue() {
        return fail(402, "参数值不对");
    }

    public static Object unlogin() {
        return fail(501, "请登录");
    }

    public static Object serious() {
        return fail(502, "系统内部错误");
    }

    public static Object unsupport() {
        return fail(503, "业务不支持");
    }

    public static Object updatedDateExpired() {
        return fail(504, "更新数据已经失效");
    }

    public static Object updatedDataFailed() {
        return fail(505, "更新数据失败");
    }

    public static Object unauthz() {
        return fail(506, "无操作权限");
    }
}
