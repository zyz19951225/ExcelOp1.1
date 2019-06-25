package com.example.demo.Config;

import com.example.demo.Handler.FileUploadInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {


    /**
     * 配置拦截器
     * @author
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FileUploadInterceptor()).addPathPatterns("/upload/**");
    }

//    /**
//     * spring boot 定时任务
//     */
//    @Scheduled(cron="0 0 22 * * ?")
//    public void reportCurrentTime() {
//      do...
//    }
}

