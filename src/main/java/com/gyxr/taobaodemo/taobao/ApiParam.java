package com.gyxr.taobaodemo.taobao;

public interface ApiParam {

    String stringfy();

    static ApiParam fromString(String s){
        return new StringParam(s);
    }

    static ApiParam fromArray(String... array){
        return new ArrayParam(array);
    }
}
