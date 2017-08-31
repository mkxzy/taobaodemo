package com.gyxr.taobaodemo.taobao;

public class StringParam implements ApiParam {

    private final String value;

    public StringParam(String value){
        this.value = value;
    }

    @Override
    public String stringfy() {
        return value;
    }
}
