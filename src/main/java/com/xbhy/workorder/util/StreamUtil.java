package com.xbhy.workorder.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class StreamUtil {


    /**
     * 从输入流中读取转化为String
     * @param in
     * @return
     */
    public static String getStreamString(InputStream in) {
        if (in != null) {
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String oneLine = StringUtils.EMPTY;
                while ((oneLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(oneLine);
                }
                //return URLDecoder.decode(stringBuilder.toString().trim(),"utf-8");
                return stringBuilder.toString().trim();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return StringUtils.EMPTY;
    }

}
