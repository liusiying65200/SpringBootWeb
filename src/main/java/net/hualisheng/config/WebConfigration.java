package net.hualisheng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.hualisheng.interceptor.LogHandlerInterceptor;
@Configuration
public class WebConfigration extends WebMvcConfigurerAdapter {

	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor interceptor=new LogHandlerInterceptor();
		System.err.println("==============添加拦截球==============");
		registry.addInterceptor(interceptor);
	}
}
