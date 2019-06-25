package com.example.demo.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.error.EmOpratingError;
import com.example.demo.error.ExcelOprateErr;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;





/**
 *  * @ClassName: FileUploadInterceptor 
 *  * @Description: 文件上传拦截过滤
 *  
 */

public class FileUploadInterceptor implements HandlerInterceptor {

    //文件大小 M  b*1kb*ikb
    private long maxSize =10*1024*1024;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");

        if (request != null && ServletFileUpload.isMultipartContent(request)) {
            ServletRequestContext ctx = new ServletRequestContext(request);
            long requestSize = ctx.contentLength();
            System.out.println(requestSize);
            if (requestSize > maxSize) {
                throw new ExcelOprateErr(EmOpratingError.UNKNOW_ERROR,"文件太大");
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}
