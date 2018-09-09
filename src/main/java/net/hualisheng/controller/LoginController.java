package net.hualisheng.controller;

import net.hualisheng.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 倘若想要使用jsp就不能使用@RestController这个注解
 * 在spring boot中想要使用jsp,需要在pom文件中添加tomcat-embed-jasper依赖
 * 在配置项中需要添加
 * spring.mvc.view.prefix=/WEB-INF/jsp/
   spring.mvc.view.suffix=.jsp
 *
 * @RestController这个注解主要是使用在返回JSON数据的时候使用
 */
@Controller
public class LoginController {
	@PostMapping("/login")
	public String login(User user) {
		if (user != null) {
			if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
				return "err";
			} else {
				return "home";
			}
		}
		return "err";
	}

	/**
	 * 给jsp传参
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("username", "root");
		return "login";

	}
}
