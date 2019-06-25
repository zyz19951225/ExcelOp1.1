package com.example.demo.error;

public interface CommonErr {

    public int getErrCode();

    public String getErrMsg();

    public CommonErr setErrMsg(String errMsg);
}
