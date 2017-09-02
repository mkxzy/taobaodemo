package com.gyxr.taobaodemo.taobao;

/**
 * 淘宝返回内容解码器
 * @param <T> 返回类型
 */
public interface ContentDecoder<T> {

    T decode(String rawString);

//    static ContentDecoder<Object> Empty(){
//
//        return s -> null;
//    }
}