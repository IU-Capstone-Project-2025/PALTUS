package com.paltus.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {

    public UserController() { }
    @GetMapping("/hello")
    public String getMethodName() {
        return "Hello test2";
    }
    
}
