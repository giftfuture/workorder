package com.xbhy.workorder.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 错误信息处理类。
 *
 * @author ycp
 */
@Slf4j
public class ExceptionUtil
{
    /**
     * 获取exception的详细错误信息。
     */
    public static String getExceptionMessage(Throwable e)
    {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        return str;
    }

    public static String getRootErrorMseeage(Exception e)
    {
        Throwable root = ExceptionUtils.getRootCause(e);
        root = (root == null ? e : root);
        if (root == null)
        {
            return "";
        }
        String msg = root.getMessage();
        if (msg == null)
        {
            return "null";
        }
        log.error(StringUtils.defaultString(msg));
        return StringUtils.defaultString(msg);
    }

    public static String printCallStatck(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        if(ex != null) {
            StackTraceElement[] stackElements = ex.getStackTrace();
            if (stackElements != null) {
                for (int i = 0; i < stackElements.length; i++) {
                    sb.append(stackElements[i].getClassName() + "\n\t" + stackElements[i].getFileName() + "\n\t"
                            + stackElements[i].getLineNumber() + "\n\t" + stackElements[i].getMethodName());
                    sb.append(System.getProperty("line.separator"));
                }
            }
        }else{
            sb.append("NullPointerException");
        }
        log.error(sb.toString());
        return sb.toString();
    }

    public static String getStackMsg(Exception e) {
        StringBuffer sb = new StringBuffer();
        if(e != null){
            StackTraceElement[] stackArray = e.getStackTrace();
            if(stackArray != null && stackArray.length > 0) {
                for (StackTraceElement element: stackArray) {
                    sb.append(element.getClassName() + "\t" + element.getFileName() + "\t"
                            + element.getLineNumber() + "\t" + element.getMethodName());
                    sb.append(System.getProperty("line.separator"));
                }
            }
        }else{
            sb.append("NullPointerException");
        }
        log.error(sb.toString());
        return sb.toString();
    }

    public static String getStackMsg(Throwable e) {
        StringBuffer sb = new StringBuffer();
        if(e != null){
            StackTraceElement[] stackArray = e.getStackTrace();
            if(stackArray != null && stackArray.length > 0) {
                for (StackTraceElement element: stackArray) {
                    sb.append(element.getClassName() + "\t" + element.getFileName() + "\t"
                            + element.getLineNumber() + "\t" + element.getMethodName());
                    sb.append(System.getProperty("line.separator"));
                }
            }
        }else{
            sb.append("NullPointerException");
        }
        log.error(sb.toString());
        return sb.toString();
    }
}
