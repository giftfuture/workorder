package com.xbhy.workorder.util;

import com.xbhy.workorder.enums.OrderSortEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenUtils {

//    public static String genSendCode(String code){
//        return new StringBuffer(code).append(DateUtils.dateTimeNow()).append((int)1e3*Math.random()).toString();
//    }
    public static String keyWordIdx = "0123456789";
    public static final int keyWordsLen = 10;
    //工单号长度为19位
    public static final int orderNoLen = 13;
    public static final List<String> OrderSortCodes = Arrays.stream(OrderSortEnum.values()).map(OrderSortEnum::getCode).collect(Collectors.toList());

    public static String genOrderCode(String code){
        return new StringBuffer(code).append(DateUtils.dateTimeNowSSS()).toString();
    }

    public static void main(String[] args) {
       String code = genOrderCode(OrderSortEnum.SENDORDER.getCode());
        System.out.println(code);
    }

}
