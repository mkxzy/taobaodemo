package com.gyxr.taobaodemo.taobao;

public interface ContentDecoder<T> {

    T decode(String rawString);

    static ContentDecoder<Object> Empty(){

        return s -> null;
    }
}