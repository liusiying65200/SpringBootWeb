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
