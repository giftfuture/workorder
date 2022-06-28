package com.xbhy.workorder.util;

import com.alibaba.druid.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: lin kq
 * @Date: 2020/11/27
 * @Version: 1.0.0
 */
public class ArgumentUtil {

    public static boolean notEmpty(String... args) {
        for(String arg : args) {
            if(StringUtils.isEmpty(arg)) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNull(Object... args) {
        for(Object arg : args) {
            if(arg == null) {
                return false;
            }
        }
        return true;
    }

    public static Integer[] insertIntArr(Integer[] src, Integer e) {
        if(src == null || src.length == 0) {
            Integer[] target = {e};
            return target;
        } else {
            Integer[] target = new Integer[src.length + 1];
            System.arraycopy(src, 0, target, 0, src.length);
            target[src.length] = e;
            return target;
        }
    }

    public static Integer[] removeIntArr(Integer[] src, Integer e) {
        if(src != null || src.length != 0 && e != null) {
            List<Integer> list = new ArrayList<>(src.length);
            Collections.addAll(list, src);
            if(list.contains(e)) {
                Iterator<Integer> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Integer curVal = iterator.next();
                    if (e.equals(curVal)) {
                        list.remove(curVal);
                        break;
                    }
                }
            }
            return list.toArray(new Integer[list.size()]);
        } else {
            return src;
        }
    }
}
