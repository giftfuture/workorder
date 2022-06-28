package com.xbhy.workorder.util;

public class ValidMsg {

    public static final String MAX_SHORTNAME ="名字中文首字母最长为8个字母";
    public static final String MUST_LETTER ="名字首字母必须为英文字母";
    public static final String LEN_TRUENAME ="真实姓名最长为64个字符(中文算一个字符)";
    public static final String ENCN_TRUENAME ="真实姓名必须为中文或英文";
    public static final String NOTNULL_USERNAME ="用户名不能为空";
    public static final String NOTNULL_PWD ="密码不能为空";
    public static final String LEN_USERNAME ="用户名最长为64个字符(中文算一个字符)";
    public static final String MUST_ENCNLETTER ="用户名必须为中文或英文、数字、下划线和美元符";
    public static final String VALID_PWD ="密码长度最小为8，最长为255个字符";
    public static final String VALID_CONFIRMPWD ="确认密码长度最小为8，最长为255个字符，且需要与密码相同";
    public static final String VALID_AVATAR ="头像最长为255个字符";
    public static final String VALID_GENDER ="性别输入只能为(1:男,2:女,3:保密)";
    public static final String VALID_CARDTYPE ="证件类型只能是(1:身份证,2:港澳台居民身份证,3:护照,4:外国人永久居留身份证,5:军官证)";
    public static final String MAX_CARDNO ="证件号若为身份证必须为15位或18位中国居民身份证号或港澳台地区10位身份证号";
    public static final String MAX_CARDTERM ="身份证有效时间长度不能大于20位";
    public static final String MOBILE= "手机号码格式不正确";
    public static final String VALID_EMAIL ="邮箱格式不正确";
    public static final String MAX_FIXPHONE = "固定电话最长不超过20位";
    public static final String MAX_EMAIL = "邮箱长度不超过20位";


}
