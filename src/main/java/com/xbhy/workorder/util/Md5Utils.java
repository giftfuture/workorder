package com.xbhy.workorder.util;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Md5Utils {
    //盐加密
    public static String md5Password(String password,String salt){
        //salt至少10位
        if(salt.length()<10){
            salt= Md5Utils.leftPad(salt,10,"0");
        }
        StringBuilder  pwd=new StringBuilder(password+"c"+salt);
        pwd=pwd.insert(1,"G");
        pwd=pwd.insert(2,salt.substring(1));
        pwd=pwd.insert(4,"5");
        pwd=pwd.insert(5,salt.substring(2,4));
        pwd=pwd.insert(7,"_");
        pwd=pwd.insert(8,salt.substring(0,3));
        //return  pwd.toString();  md5
        return null;//new BCryptPasswordEncoder().encode(pwd.toString());
    }
    //字符串格式化长度不足补0
    public static String leftPad(String str, int strLength,String chart) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append(chart).append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    public static void main(String[] args) {

    }
}
