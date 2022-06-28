package com.xbhy.workorder.enums;

/**
 * 角色Key
 *
 * @author
 */
public enum RoleKey {
    ADMIN(1, "sys_usr"), STAFF(2, "staf");

    private final Integer code;
    private final String key;

    RoleKey(Integer code, String key) {
        this.code = code;
        this.key = key;
    }


    public Integer getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }
}
