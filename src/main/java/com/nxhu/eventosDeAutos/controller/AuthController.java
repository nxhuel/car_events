package com.nxhu.eventosDeAutos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    @GetMapping("/get")
    public String helloGet() {
        return "Hello World";
    }
}
