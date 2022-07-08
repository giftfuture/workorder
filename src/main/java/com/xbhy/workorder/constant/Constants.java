/**
 * 上海悦商
 * com.yueworldframework.core.support
 * Constants.java
 * <p>
 * 2016-01-04
 * 2016 上海悦商-版权所有
 */
package com.xbhy.workorder.constant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Constants
 *
 * @author
 * @version 1.0.0
 * @time 16:19
 * @email
 * @description 通用 常量类
 */

public class Constants {
    public final static int HIGHEST_PRECEDENCE = -2147483648;
    public final static int LOWEST_PRECEDENCE = 2147483647;
    public final static BigDecimal MAX_BIG_DECIMAL = new BigDecimal("9999999999");
    /**
     * Tools
     */
    /*================== 工具 =============================================================================================*/
    public final static String LINE = "=====================================================================";
    public final static String LINE_HALF = "=================================";
    public final static String MODEL_LINES[] = new String[]{"\n\t----------------[  Model View  ]------------------\n\t     key        |  value\n\t--------------------------------------------------", "--------------------------------------------------"};

    /**
     * 逻辑常量
     */
    /*================== YES,NO =============================================================================================*/
    public final static Integer YES = 1;
    public final static Integer NO = 2;
    // 文件分割符
    public final static String fileSeparator = System.getProperty("file.separator");

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final Integer SUCCESS = 0;

    /**
     * 通用失败标识
     */
    public static final Integer FAIL = 1;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 自动去除表前缀
     */
    public static final String AUTO_REOMVE_PRE = "true";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    public static final String KAPTCHA_SESSION_KEY = "xbhySession";
}
