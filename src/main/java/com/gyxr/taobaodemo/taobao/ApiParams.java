package com.gyxr.taobaodemo.taobao;

/**
 * Api参数工厂方法
 */
public final class ApiParams {

    public static ApiParam stringParam(String s){

        return () -> s;
    }

    public static ApiParam arrayParam(String... array){

        return () -> String.join(",", array);
    }
}
