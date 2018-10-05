package net.hualisheng.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/freemarker")
public class FreeMarkerController {
	@GetMapping(value = "/index")
	public String name(Model model) {
		model.addAttribute("user", "root");
		model.addAttribute("item", "741141");
		List<String> userList = new ArrayList<String>();
		userList.add("admin");
		userList.add("user1");
		userList.add("user2");

		model.addAttribute("userList", userList);
		return "index1";

	}

	@RequestMapping(value = "/login")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("index2");

		List<String> userList = new ArrayList<String>();
		userList.add("admin");
		userList.add("user1");
		userList.add("user2");

		modelAndView.addObject("userList", userList);
		return modelAndView;
	}
}
