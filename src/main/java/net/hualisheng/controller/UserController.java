package net.hualisheng.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @RequestMapping(value = "/user/home",method = RequestMethod.POST )
    @ResponseBody
    public String home(@RequestParam("name")String username,@RequestParam("pwd")String password){
        return "user home";
    }
    @GetMapping(value = "/user/show")
    @ResponseBody
    public String show(){
        return "user home";
    }
    @PostMapping(value = "/user/port")
    @ResponseBody
    public String port(@RequestParam(value = "name",required = true,defaultValue = "admin") String name,@RequestParam(value = "pwd",required = true,defaultValue = "123") String pwd){
        return "user/port"+"名字:"+name+";密码:"+pwd;
    }
    @ResponseBody
    @GetMapping(value = "/user/{id}")
    public String showId(@PathVariable String id){
        return id;
    }
    @GetMapping("/user/ip")
    public String showIp(HttpServletRequest request, HttpServletResponse response){
        return "ip:"+request.getRemoteHost();
    }
}
