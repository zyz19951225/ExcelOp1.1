package com.example.demo.Controller;

import com.example.demo.error.EmOpratingError;
import com.example.demo.error.ExcelOprateErr;
import com.example.demo.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BaseController {
    //定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
        System.out.println("捕获异常，进行异常消息处理输出");
        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof ExcelOprateErr) {
            ExcelOprateErr excelOprateErr = (ExcelOprateErr) ex;
            System.out.println("ErrCode:"+excelOprateErr.getErrCode()+"  ErrMsg:"+excelOprateErr.getErrMsg());
            responseData.put("errCode", excelOprateErr.getErrCode());
            responseData.put("errMsg", excelOprateErr.getErrMsg());
        } else {
            responseData.put("errCode", EmOpratingError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg", EmOpratingError.UNKNOW_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData, "fail");
    }

}
