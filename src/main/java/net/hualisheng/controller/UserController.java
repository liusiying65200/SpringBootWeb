package net.hualisheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
