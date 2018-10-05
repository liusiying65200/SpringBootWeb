package net.hualisheng.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestDemoController {
    @GetMapping("/rest")
    public String home(){
        return "rest";
    }
}
