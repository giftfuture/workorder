package com.xbhy.workorder.constant;

/**
 * @Description:
 * @Author: Athrun
 * @Date: 2020/11/27
 * @Version 1.0
 */
public class AuthConstant {

    // 匿名权限, 0-否, 1-是
    public static final Integer PERM_ANNO_STATUS_YES = 1;
    public static final Integer PERM_ANNO_STATUS_NO = 0;

    // 角色不存在
    public static int ROLE_NOT_EXIST_CODE = 2001;
    public static String ROLE_NOT_EXIST_ERROR = "角色不存在";

    // 权限不存在
    public static int PERM_NOT_EXIST_CODE = 2002;
    public static String PERM_NOT_EXIST_ERROR = "权限不存在";
}
