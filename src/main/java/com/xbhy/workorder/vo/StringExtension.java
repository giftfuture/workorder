package com.xbhy.workorder.vo;

/**
 * Create by 孙斌 on 2019/5/16
 */
public class StringExtension {


    public static Boolean isNullOrEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String maxCityId(String cityId) {
        if(isNullOrEmpty(cityId) || cityId.length() < 6){
            return cityId;
        }
        if(cityId.substring(2,6).equals("0000")){
            cityId = cityId.substring(0,2) + "9999";
        }
        if(cityId.substring(4,6).equals("00")){
            cityId = cityId.substring(0,4) + "99";
        }
        return cityId;
    }

    //用于判断城市最大code
    public static String findMaxCityId(String cityId_1,String cityId_2,String cityId_3){
        String mapCityId = "";
        if(!StringExtension.isNullOrEmpty(cityId_1)){
            mapCityId = cityId_1;
        }
        if(!StringExtension.isNullOrEmpty(cityId_2)){
            mapCityId = cityId_2;
        }
        if(!StringExtension.isNullOrEmpty(cityId_3)){
            mapCityId = cityId_3;
        }
        return mapCityId;
    }

}
