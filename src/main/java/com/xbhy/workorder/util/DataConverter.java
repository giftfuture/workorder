package com.xbhy.workorder.util;



import com.xbhy.workorder.exception.TypeCastException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 数据转换
 * @author:
 * @time:
 */
public class DataConverter {


    private DataConverter() {

    }
    public static final Integer degreeOfPraise = 2;


    public static final Integer quantitySize = 3;

    public static final Integer credit = 1;
    //钱
    public static final Integer freight = 2;


    //——是否添加尾号
    public static final boolean MAXCONVERT_TAIL_TRUE = true;
    public static final boolean MAXCONVERT_TAIL_FALSE = false;

    public static final String convertIntegerToString(Integer integer) {
        if (integer == null) {
            return null;
        }
        return integer.toString();
    }


    public static final String convertStringToDigitString(String str, int digits) {
        if (str == null) {
            return null;
        }
        String result = "";
        int length = (digits - str.length());
        for (int i = 0; i < length; i++) {
            result = result + "0";
        }

        return result + str;
    }


    public static final String convertShortToString(Short parashort) {
        if (parashort == null) {
            return null;
        }
        return parashort.toString();
    }

    public static final String convertBigDecimalToString(BigDecimal bigDecimal,
                                                         int Scale) {

        if (bigDecimal == null) {
            return "0";
        }
        BigDecimal b = bigDecimal.setScale(Scale, BigDecimal.ROUND_HALF_UP);
        String bigDecimalStr  = b.toString();
        if(Scale==2){
            if(".00".equals(bigDecimalStr.substring(bigDecimalStr.length()-3,bigDecimalStr.length()))){
                bigDecimalStr = bigDecimalStr.substring(0,bigDecimalStr.length()-3);
            }else if("0".equals(bigDecimalStr.substring(bigDecimalStr.length()-1,bigDecimalStr.length()))){
                bigDecimalStr = bigDecimalStr.substring(0,bigDecimalStr.length()-1);
            }else if(".0".equals(bigDecimalStr.substring(bigDecimalStr.length()-2,bigDecimalStr.length()))){
                bigDecimalStr = bigDecimalStr.substring(0,bigDecimalStr.length()-2);
            }
        }
        if(Scale==3){
            if(".000".equals(bigDecimalStr.substring(bigDecimalStr.length()-4,bigDecimalStr.length()))){
                bigDecimalStr = bigDecimalStr.substring(0,bigDecimalStr.length()-4);
            }else if("00".equals(bigDecimalStr.substring(bigDecimalStr.length()-2,bigDecimalStr.length()))){
                bigDecimalStr = bigDecimalStr.substring(0,bigDecimalStr.length()-2);
            }else if("0".equals(bigDecimalStr.substring(bigDecimalStr.length()-1,bigDecimalStr.length()))){
                bigDecimalStr = bigDecimalStr.substring(0,bigDecimalStr.length()-1);
            }
        }
        return bigDecimalStr;
    }

    public static final String convertBigDecimalToStringRoundup(BigDecimal bigDecimal,
                                                                int Scale) {

        if (bigDecimal == null) {
            return "";
        }
        BigDecimal b = bigDecimal.setScale(Scale, BigDecimal.ROUND_UP);

        return b.toString();
    }

    public static final BigDecimal scalBigDecimal   (String num, int Scale) {
        BigDecimal bigDecimal=null;
        bigDecimal= new BigDecimal(num);
        bigDecimal = bigDecimal.setScale(Scale, BigDecimal.ROUND_UP);
        return  bigDecimal;
    }

    public static final BigDecimal convertStringToDecimal(String num) {
        BigDecimal bigDecimal = null;
        try {
            bigDecimal = new BigDecimal(num);
        } catch (Exception e) {
            System.out.println("FormatUtil.convertStringToDecimal(" + num
                    + ") failed with the errorMessage:" + e.getMessage());
        }
        return bigDecimal;
    }

    public static final Integer StringToInt(String aStr) {
        Integer integer = null;

        try {
            if (aStr == null || aStr.trim().equals("")) {
                return null;
            } else {
                integer = new Integer(aStr);
            }

        } catch (Exception e) {
            System.out.println("FormatUtil.StringToInt(" + aStr
                    + ") failed with the errorMessage:" + e.getMessage());
        }
        return integer;
    }

    public static final boolean isEmpty(String str) {

        return (str == null || str.trim().length() == 0) ? true : false;
    }

    public static final boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static final String trimNull(String str) {
        return (isEmpty(str) ? null : str.trim());
    }

    public static final BigDecimal convertIntegerToBigDecimal(Integer integer) {
        return (integer == null ? null : BigDecimal.valueOf(integer));
    }

    public static final BigDecimal convertLongToBigDecimal(Long para) {
        return (para == null ? null : BigDecimal.valueOf(para));
    }

    public static final BigInteger convertIntegerToBigInteger(Integer integer) {
        return (integer == null ? null : BigInteger.valueOf(integer));
    }

    public static final Integer convertBigIntegerToInteger(BigInteger bigInteger) {
        return (bigInteger == null ? null : bigInteger.intValue());
    }

    public static final Integer convertBigDecimalToInteger(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.intValue();
    }

    public static final Long StringToLong(String aStr) {
        Long longTemp = null;
        try {
            longTemp = new Long(aStr);
        } catch (Exception e) {
            System.out.println("FormatUtil.StringToLong(" + aStr
                    + ") failed with the errorMessage:" + e.getMessage());
        }
        return longTemp;
    }

    public static final Integer convertLongToInteger(Long para) {
        return (para == null ? null : para.intValue());
    }

    public static String subString(String str, int maxBytesLength) {
        if (isNotEmpty(str) && str.getBytes().length > maxBytesLength) {
            byte[] bytesOut = new byte[maxBytesLength];
            byte[] bytesIn = str.getBytes();
            System.arraycopy(bytesIn, 0, bytesOut, 0, maxBytesLength);
            str = new String(bytesOut);
        }
        return str;
    }

    public static final Long convertBigDecimalToLong(BigDecimal bigDecimal) {
        String strTemp = convertBigDecimalToString(bigDecimal, 0);
        Long longTemp = StringToLong(strTemp);
        return longTemp;
    }

    public static final BigInteger convertBigDecimalToBigInteger(
            BigDecimal bigDecimal) {
        Integer intTemp = convertBigDecimalToInteger(bigDecimal);
        BigInteger bigIntegerTemp = convertIntegerToBigInteger(intTemp);
        return bigIntegerTemp;
    }

    public static String bigDecimalToStringDistance(BigDecimal bigDecimal) {
        if (bigDecimal == null)
            return "";
        if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
            return "0.00";
        }
        BigDecimal decimal = bigDecimal.divide(new BigDecimal(1000));

        if (decimal.compareTo(new BigDecimal(0.1))<0){
            decimal = new BigDecimal(0.1);
        }
        return bigDecimalToString(decimal);
    }

    public static String bigDecimalToString(BigDecimal bigDecimal) {
        if (bigDecimal == null)
            return "";
        if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String  bigDecimalStr = df.format(bigDecimal);
        return bigDecimalStr;
    }


    public static BigDecimal isZero(BigDecimal bigDecimal) {
        BigDecimal result=new BigDecimal("0.00");
        if (bigDecimal == null)
            return result;
        if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
            return result;
        }
        return bigDecimal.setScale(2, BigDecimal.ROUND_UP);
    }

    public static String hiddenIdNo(String idNo){
        idNo=idNo.trim();
        Integer length=idNo.length();
        if(StringUtils.isBlank(idNo))return "";

        if(length==15) {
            idNo=idNo.substring(0,6)+"******"+idNo.substring(length-3,length);
        }else{
            idNo=idNo.substring(0,6)+"********"+idNo.substring(length-4,length);
        }
        return idNo;
    }


    /**
     * 加密手机号或者身份证
     * @param idNo
     * @param mobile
     * @return
     */
    public static String hiddenIdNoOrMobile(String idNo,String mobile){
        if(!StringUtils.isEmpty(idNo)){
            idNo=idNo.trim();
            Integer length=idNo.length();
            if(StringUtils.isBlank(idNo))return "";

            if(length==15) {
                idNo=idNo.substring(0,6)+"******"+idNo.substring(length-3,length);
            }else{
                idNo=idNo.substring(0,6)+"********"+idNo.substring(length-4,length);
            }
            return idNo;
        }else if(!StringUtils.isEmpty(mobile)){
            mobile=mobile.trim();
            mobile=mobile.substring(0,3)+"****"+mobile.substring(mobile.length()-4,mobile.length());
            return mobile;
        }

        return emptyString();
    }


    public static final BigDecimal stringToDecimal(String num) {
        BigDecimal bigDecimal =BigDecimal.ZERO;
        if(StringUtils.isNotBlank(num)){
            bigDecimal = new BigDecimal(num);
        }
        return bigDecimal;
    }

    public static final String emptyString(){
        return "";

    }

    public static final String stringIsEmpty(String string){
        return StringUtils.isEmpty(string) ? emptyString() : string;

    }

    public static final Integer stringToInteger(String string){
        return StringUtils.isEmpty(string) ? 0 : Integer.parseInt(string);
    }

    public static final Integer integerIsEmpty(Integer integer){

        return integer == null || StringUtils.isEmpty(integer+"") ? 0 : integer;
    }

    public static final String integerToString(Integer integer){

        return integer ==null  ? emptyString() : integer+"";
    }

    public static final String integerToString0(Integer integer){

        return integer ==null ||  integer == 0 ? "0" : integer+"";
    }

    public static boolean objectIsNull(Object o){
        return	o==null ? false : true;
    }

    public static boolean isBetweenthetwo(String begin,String end,String value){
        double min = Double.parseDouble(stringIsEmpty(begin));
        double max = Double.parseDouble(stringIsEmpty(end));
        double number = Double.parseDouble(stringIsEmpty(value));

        if(number >= min && number <= max){
            return true;
        }

        return false;
    }

    public static String aChoiceOfExist(String str1,String str2){
        return StringUtils.isEmpty(str1) ? str2 : str1;
    }

    /**
     * 数字上限
     * @param num
     * @param digits  位数
     * @param tail  是否需要尾号  +
     * @return
     */
    public static String maxConvert(Integer num,int digits,boolean tail){
        if(num == null){
            return "0";
        }

        //获取上限位数
        StringBuilder showMax = new StringBuilder("9");
        for (int i = 0; i <(digits-1); i++) {
            showMax.append("9");
        }

        if(num.intValue() > stringToInteger(showMax.toString())){
            if(tail){
                showMax.append("+");
            }
            return showMax.toString();
        }
        return integerToString0(num);
    }

    public static String insertSomeString(String str1,int index,String str2){
        StringBuilder sb = new StringBuilder(str1);
        sb.insert(index,str2);
        return sb.toString();
    }


    public static boolean lessThanAndEqualEZero(BigDecimal bigDecimal) {
        if (bigDecimal == null){
            return true;
        }
        if (bigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }
        return false;
    }

    public static BigDecimal bigDecimalIsEmpty(BigDecimal bigDecimal) {
        if (bigDecimal == null || (bigDecimal.compareTo(BigDecimal.ZERO) == 0)){
            return BigDecimal.ZERO;
        }
        return bigDecimal;
    }

    /**
     * Integer为空或小于0，为true
     * @author
     * @param input
     * @return
     */
    public static boolean isIntegerEmpty(Integer input) {
        if ((input == null) || (input.intValue() <= 0)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static BigDecimal trimBigDecimal(String param){
        if(StringUtils.isBlank(param)){
            return BigDecimal.ZERO;
        }
        return new BigDecimal(param);
    }

    /**
     * 说明：判断是否为BigDecimal
     * @param str
     * @return
     */
    public static boolean isBigDecimal(String str) {
        if(str == null){
            return false;
        }
        try{
            BigDecimal bd =  new BigDecimal(str);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static BigDecimal calcBigDecimal(BigDecimal a, BigDecimal b){
        if(a==null){
            a=new BigDecimal(0);
        }
        a.add(b==null?new BigDecimal(0):b);
        return a;
    }

    public static Integer calcInteger(Integer a,Integer b){
        if(a==null){
            a=new Integer(0);
        }
        if(b==null){
            b=new Integer(0);
        }
        return a+b;
    }
    public static String castToString(Object value) {
        return castToString(value, null);
    }

    public static String castToString(Object value, String defaultValue) {
        return value == null ? defaultValue : value.toString();
    }


    public static Byte castToByte(Object value) {
        return castToByte(value, null);
    }


    public static Byte castToByte(Object value, Byte defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Number) {
            return ((Number) value).byteValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            return strVal.length() == 0 ? null : Byte.parseByte(strVal);
        } else {
            throw new TypeCastException("can not cast to byte, value : " + value);
        }
    }


    public static Character castToChar(Object value) {
        return castToChar(value, null);
    }

    public static Character castToChar(Object value, Character defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Character) {
            return (Character) value;
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            } else if (strVal.length() != 1) {
                throw new TypeCastException("can not cast to byte, value : " + value);
            } else {
                return strVal.charAt(0);
            }
        } else {
            throw new TypeCastException("can not cast to byte, value : " + value);
        }
    }

    public static Short castToShort(Object value) {
        return castToShort(value, null);
    }


    public static Short castToShort(Object value, Short defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Number) {
            return ((Number) value).shortValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            return strVal.length() == 0 ? null : Short.parseShort(strVal);
        } else {
            throw new TypeCastException("can not cast to short, value : " + value);
        }
    }


    public static BigDecimal castToBigDecimal(Object value) {
        return castToBigDecimal(value, null);
    }


    public static BigDecimal castToBigDecimal(Object value, BigDecimal defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        } else {
            String strVal = value.toString();
            return strVal.length() == 0 ? null : new BigDecimal(strVal);
        }
    }


    public static BigInteger castToBigInteger(Object value) {
        return castToBigInteger(value, null);
    }

    public static BigInteger castToBigInteger(Object value, BigInteger defualtValue) {
        if (value == null) {
            return defualtValue;
        } else if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else if (!(value instanceof Float) && !(value instanceof Double)) {
            String strVal = value.toString();
            return strVal.length() == 0 ? null : new BigInteger(strVal);
        } else {
            return BigInteger.valueOf(((Number) value).longValue());
        }
    }


    public static Float castToFloat(Object value) {
        return castToFloat(value, null);
    }


    public static Float castToFloat(Object value, Float defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else if (value instanceof String) {
            String strVal = value.toString();
            return strVal.length() == 0 ? null : Float.parseFloat(strVal);
        } else {
            throw new TypeCastException("can not cast to float, value : " + value);
        }
    }


    public static Double castToDouble(Object value) {
        return castToDouble(value, null);
    }

    public static Double castToDouble(Object value, Double defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            String strVal = value.toString();
            return strVal.length() == 0 ? null : Double.parseDouble(strVal);
        } else {
            throw new TypeCastException("can not cast to double, value : " + value);
        }
    }

    public static Date castToDate(Object value) {
        return castToDate(value, null);
    }


    public static Date castToDate(Object value, Date defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        } else if (value instanceof Date) {
            return (Date) value;
        } else {
            long longValue = 0L;
            if (value instanceof Number) {
                longValue = ((Number) value).longValue();
            }

            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.indexOf(45) != -1) {
                    String format;
                    if (strVal.length() ==  DateStyle.YYYY_MM_DD_HH_MM_SS.getValue().length()) {
                        format = DateStyle.YYYY_MM_DD_HH_MM_SS.getValue(); //Constant.DEFAULT_DATE_FORMAT
                    } else if (strVal.length() == 10) {
                        format = "yyyy-MM-dd";
                    } else if (strVal.length() == "yyyy-MM-dd HH:mm".length()) {
                        format = "yyyy-MM-dd HH:mm";
                    } else {
                        format = "yyyy-MM-dd HH:mm.SSS";
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat(format);

                    try {
                        return dateFormat.parse(strVal);
                    } catch (ParseException var7) {
                        throw new TypeCastException("can not cast to Date, value : " + strVal);
                    }
                }

                if (strVal.length() == 0) {
                    return null;
                }

                longValue = Long.parseLong(strVal);
            }

            if (longValue <= 0L) {
                throw new TypeCastException("can not cast to Date, value : " + value);
            } else {
                return new Date(longValue);
            }
        }
    }

    public static java.sql.Date castToSqlDate(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar) value).getTimeInMillis());
        } else if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        } else if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        } else {
            long longValue = 0L;
            if (value instanceof Number) {
                longValue = ((Number) value).longValue();
            }

            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.length() == 0) {
                    return null;
                }

                longValue = Long.parseLong(strVal);
            }

            if (longValue <= 0L) {
                throw new TypeCastException("can not cast to Date, value : " + value);
            } else {
                return new java.sql.Date(longValue);
            }
        }
    }

    public static Timestamp castToTimestamp(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Calendar) {
            return new Timestamp(((Calendar) value).getTimeInMillis());
        } else if (value instanceof Timestamp) {
            return (Timestamp) value;
        } else if (value instanceof Date) {
            return new Timestamp(((Date) value).getTime());
        } else {
            long longValue = 0L;
            if (value instanceof Number) {
                longValue = ((Number) value).longValue();
            }

            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.length() == 0) {
                    return null;
                }

                longValue = Long.parseLong(strVal);
            }

            if (longValue <= 0L) {
                throw new TypeCastException("can not cast to Date, value : " + value);
            } else {
                return new Timestamp(longValue);
            }
        }
    }

    public static Long castToLong(Object value) {
        return castToLong(value, null);
    }

    public static Long castToLong(Object value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            return strVal.length() == 0 ? null : Long.parseLong(strVal);
        } else {
            throw new TypeCastException("can not cast to long, value : " + value);
        }
    }

    public static Integer castToInt(Object value) {
        return castToInt(value, null);
    }

    public static Integer castToInt(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            return strVal.length() == 0 ? null : Integer.parseInt(strVal);
        } else {
            throw new TypeCastException("can not cast to int, value : " + value);
        }
    }

    public static byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else if (value instanceof String) {
            return Base64.decode((String) value);
        } else {
            throw new TypeCastException("can not cast to int, value : " + value);
        }
    }

    public static Boolean castToBoolean(Object value) {
        return castToBoolean(value, null);
    }

    public static Boolean castToBoolean(Object value, Boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        } else {
            if (value instanceof String) {
                String str = (String) value;
                if (str.length() == 0) {
                    return null;
                }

                if ("true".equals(str)) {
                    return Boolean.TRUE;
                }

                if ("false".equals(str)) {
                    return Boolean.FALSE;
                }

                if ("1".equals(str)) {
                    return Boolean.TRUE;
                }
            }

            throw new TypeCastException("can not cast to int, value : " + value);
        }
    }

}
