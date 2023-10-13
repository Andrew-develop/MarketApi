package com.example.demo.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
public class MainController {
    @GetMapping("/unsecured")
    public fun unsecuredData(): String {
        return "unsecured data"
    }

    @GetMapping("/secured")
    public fun securedData(): String {
        return "secured data"
    }

    @GetMapping("/admin")
    public fun adminData(): String {
        return "admin data"
    }

    @GetMapping("/info")
    public fun userData(principal: Principal): String {
        return principal.getName()
    }
}