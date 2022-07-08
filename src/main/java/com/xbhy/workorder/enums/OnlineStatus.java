package com.xbhy.workorder.enums;

/**
 * 用户会话
 * 
 * @author
 */
public enum OnlineStatus
{
    /** 用户状态 */
    on_line("on_line", "在线"), off_line("off_line","离线");

    private final String code;
    private final String info;

    private OnlineStatus(String code,String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }

    public String getCode() {
        return code;
    }
}
