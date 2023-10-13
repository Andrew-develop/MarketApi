package com.example.demo.services

import com.example.demo.dtos.JwtRequest
import com.example.demo.dtos.JwtResponse
import com.example.demo.dtos.RegistrationUserDto
import com.example.demo.dtos.UserDto
import com.example.demo.exceptions.AppError
import com.example.demo.utils.JwtTokenUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Service
class AuthService (
        private val userService: UserService,
        private val jwtTokenUtils: JwtTokenUtils,
        private val authenticationManager: AuthenticationManager
) {
    fun createAuthToken(@RequestBody authRequest: JwtRequest): ResponseEntity<*> {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password))
        } catch (e: BadCredentialsException) {
            return ResponseEntity(AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED)
        }
        val userDetails = userService.loadUserByUsername(authRequest.username)
        val token = jwtTokenUtils.generateToken(userDetails)
        return ResponseEntity.ok(JwtResponse(token))
    }

    fun createNewUser(@RequestBody registrationUserDto: RegistrationUserDto): ResponseEntity<*> {
        if (!registrationUserDto.password.equals(registrationUserDto.confirmPassword)) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST)
        }
        if (userService.findByUsername(registrationUserDto.username) != null) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST)
        }
        val user = userService.createNewUser(registrationUserDto)
        return ResponseEntity.ok(UserDto(user.id ?: 0, user.username, user.email))
    }
}