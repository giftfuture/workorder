package com.xbhy.workorder.vo;

import com.xbhy.workorder.util.DataConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author:
 * @time:
 */
@Data
@NoArgsConstructor
public class Response {
    private Integer code;

    private Object data;

    private String msg;

    public Response(Object msg) {
        this.msg = DataConverter.castToString(msg);
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static <T> Response fail(String message ){
        return new Response(1 , message );
    }
}
