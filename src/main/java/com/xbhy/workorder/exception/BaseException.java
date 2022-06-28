package com.xbhy.workorder.exception;


import com.xbhy.workorder.util.MessageUtils;

/**
 * 基础异常
 * 
 * @author
 */
public class BaseException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    private Throwable throwable;

    public BaseException() {}
    public BaseException(Integer code) {
        this.code = code;
    }
    public BaseException(Integer code, String message) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
    public BaseException(Integer code, String defaultMessage, Throwable throwable) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
    public BaseException(String module, Integer code, String defaultMessage)
    {
        this.module = module;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
    public BaseException(String module, Integer code, Object[] args, String defaultMessage)
    {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String module, Integer code, Object[] args)
    {
        this(module, code, args, null);
    }

    public BaseException(String module, String defaultMessage)
    {
        this(module, null, null, defaultMessage);
    }

    public BaseException(Integer code, Object[] args)
    {
        this(null, code, args, null);
    }

    public BaseException(String defaultMessage)
    {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage()
    {
        String message = null;
        message = MessageUtils.message(code, args);
        if (message == null)
        {
            message = defaultMessage;
        }
        return message;
    }

    public String getModule()
    {
        return module;
    }

    public int getCode() {
        return code;
    }

    public Object[] getArgs()
    {
        return args;
    }

    public String getDefaultMessage()
    {
        return defaultMessage;
    }
}
