package net.hualisheng.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局错误捕获
 * @author lsy
 *
 */
@ControllerAdvice
public class GlobalErrorHandler { 
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Exception globalErr(Exception exception) {
		return exception;
	}
	@ExceptionHandler(value=NullPointerException.class)
	@ResponseBody
	public String nullPointerException(Exception exception) {
		return "空指针:"+exception.getClass().getName();
	}
}
