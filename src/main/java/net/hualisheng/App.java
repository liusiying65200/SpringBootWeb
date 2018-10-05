package net.hualisheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
/**
 * 
 * @author lsy
 * 异常处理
 * 1.去掉spring自带的异常处理逻辑
 * @SpringBootApplication(exclude=ErrorMvcAutoConfiguration.class)
 * 2.配置好错误页面
 * 3.创建一个自己的错误处理类
 *
 */
@SpringBootApplication(exclude=ErrorMvcAutoConfiguration.class)
@ServletComponentScan
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
