package net.hualisheng.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/rest")
    public String home(){
        return "rest";
    }
}
