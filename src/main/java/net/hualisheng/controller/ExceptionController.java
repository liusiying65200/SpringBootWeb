package net.hualisheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/err")
public class ExceptionController {
	@GetMapping("/test")
	public String hello() {
		throw new IllegalArgumentException("arguments is err");
	}
}
