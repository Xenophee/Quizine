package com.dassonville.api.controller;


import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Hidden
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello World! " + System.getenv("SPRING_PROFILES_ACTIVE");
    }

    @GetMapping("/doc")
    public RedirectView redirectForSwaggerDoc() {
        return new RedirectView("/swagger-ui/index.html");
    }

}
