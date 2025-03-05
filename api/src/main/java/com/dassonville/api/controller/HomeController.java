package com.dassonville.api.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello World today! " + System.getenv("SPRING_PROFILES_ACTIVE");
    }
}
