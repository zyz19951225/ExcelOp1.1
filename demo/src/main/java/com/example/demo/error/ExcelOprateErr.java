package com.example.demo.error;

public class ExcelOprateErr extends Exception implements CommonErr {

   private CommonErr commonErr;


    //直接接受EmOpratingError的传参用于构造业务异常
   public ExcelOprateErr(CommonErr commonErr){
       super();
       this.commonErr=commonErr;

   }

    //接受自定义errMsg的方式构造业务异常
    public ExcelOprateErr(CommonErr commonErr, String errMsg) {
        super();
        this.commonErr = commonErr;
        this.commonErr.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonErr.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonErr.getErrMsg();
    }

    @Override
    public CommonErr setErrMsg(String errMsg) {
        this.commonErr.setErrMsg(errMsg);
        return this;
    }
}
