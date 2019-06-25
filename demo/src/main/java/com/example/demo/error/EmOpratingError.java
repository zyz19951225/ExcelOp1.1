package com.example.demo.error;

public enum EmOpratingError implements CommonErr {

    //文件名错误100*
    FILE_EMPTY(1001,"文件为空"),
    FILE_TYPE_ERROR(1002, "文件类型错误"),
    FILE_EXCEEDS_LIMIT(1003,"文件超出容量限制"),
    TABLE_NAME_ERROR(1004,"请检查文件名首字母大小写"),

    //数据库错误200*
    TABLE_NAME_NOT_FOUND(2001,"没有找到对应数据库表"),

    //未知错误
    UNKNOW_ERROR(9999,"未知错误");

    EmOpratingError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;

    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonErr setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }


}
