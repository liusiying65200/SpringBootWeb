package net.hualisheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/freemarker")
public class FreeMarkerController {
	@GetMapping(value="/index")
	public String name(Model model) {
		model.addAttribute("user", "root");
		return "index";
		
	}
}
