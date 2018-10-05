#### 第一步:讲spring自带的ErrorMvcAutoConfiguration屏蔽掉
```
@SpringBootApplication(exclude=ErrorMvcAutoConfiguration.class)
@ServletComponentScan
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
```
#### 第二步:配置好自己的错误页面
```
404.html
500.html
```
#### 第三步:创建自己的错误页面注册类并将其交给Spring托管@Component
```
package net.hualisheng.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
public class CommondErrorPageRegistry implements ErrorPageRegistrar {

	
	public void addErrorPages(ErrorPage... errorPages) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage e404=new  ErrorPage(HttpStatus.NOT_FOUND,"/error/404.html");
		ErrorPage e500=new  ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/500.html");		
		ErrorPage arguments=new  ErrorPage(IllegalArgumentException.class,"/error/arguments.html");
		// TODO Auto-generated method stub
		registry.addErrorPages(e404,e500,arguments);
//		registry.addErrorPages(e500);
	}

}


```

#### 局部处理错误捕获
```
package net.hualisheng.controller;

import java.io.FileNotFoundException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileNotFoundController {
	@ExceptionHandler(FileNotFoundException.class)
	public String fileErr(Exception exception) {
		return "文件不存在了的错误处理:"+exception.getMessage()+";"+exception.getLocalizedMessage()+";"+exception.getCause();
	}
	@ExceptionHandler(value=Exception.class)
	public String error(Exception exception) {
		return "错误类型:"+exception.getMessage();
	}
	@GetMapping("/list")
	public String bookList() throws FileNotFoundException {
		throw new FileNotFoundException("文件不存在");
	}
	@GetMapping("/class")
	public String classNotFound() throws FileNotFoundException, ClassNotFoundException {
		throw new ClassNotFoundException("类不存在");
	}
}

```
#### 全局捕获错误
```
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
		return exception.getClass().getName();
	}
}

```


