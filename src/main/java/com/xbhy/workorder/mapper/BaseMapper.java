package com.xbhy.workorder.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, InsertListSelective<T>{

    default Class<T> getTclass() {
        Class c = this.getClass();

        try {
            Class[] classes = c.getInterfaces();
            if (classes != null) {
                Class[] var3 = classes;
                int var4 = classes.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Class aClass = var3[var5];
                    Type[] types = aClass.getGenericInterfaces();
                    Type[] var8 = types;
                    int var9 = types.length;

                    for(int var10 = 0; var10 < var9; ++var10) {
                        Type t = var8[var10];
                        if (t instanceof ParameterizedType) {
                            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
                            return (Class)p[0];
                        }
                    }
                }
            }
        } catch (Exception var13) {
            var13.printStackTrace();
        }

        return null;
    }



}
