package net.hualisheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class DemoController {
	@GetMapping("/index")
	@ResponseBody
	public String home() {
		return "user home";
	}
}
