package com.example.demo.controllers

import com.example.demo.dtos.JwtRequest
import com.example.demo.dtos.RegistrationUserDto
import com.example.demo.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController (
    private val authService: AuthService
) {
    @PostMapping("/auth")
    fun createAuthToken(@RequestBody authRequest: JwtRequest): ResponseEntity<*> {
        return authService.createAuthToken(authRequest)
    }

    @PostMapping("registration")
    fun createNewUser(@RequestBody registrationUserDto: RegistrationUserDto): ResponseEntity<*> {
        return authService.createNewUser(registrationUserDto)
    }
}