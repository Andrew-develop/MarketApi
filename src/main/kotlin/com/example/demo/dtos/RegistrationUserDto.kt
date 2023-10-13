package com.example.demo.dtos

data class RegistrationUserDto (
        val username: String,
        val password: String,
        val confirmPassword: String,
        val email: String
)
