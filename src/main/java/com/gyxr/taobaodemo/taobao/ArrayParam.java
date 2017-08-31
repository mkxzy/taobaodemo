package com.gyxr.taobaodemo.taobao;

public class ArrayParam implements ApiParam {

    private final String[] array;

    public ArrayParam(String... array){
        this.array = array;
    }

    @Override
    public String stringfy() {
        return String.join(",", array);
    }
}
