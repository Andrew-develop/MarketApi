package com.example.demo.services

import com.example.demo.dtos.RegistrationUserDto
import com.example.demo.repositories.UserRepository
import com.example.demo.entities.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class UserService(
        private val userRepository: UserRepository,
        private val roleService: RoleService,
        private val passwordEncoder: BCryptPasswordEncoder
): UserDetailsService {
    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = findByUsername(username) ?: throw UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        )
        return org.springframework.security.core.userdetails.User (
                user.username,
                user.password,
                user.roles.stream().map { SimpleGrantedAuthority(it.name) }.collect(Collectors.toList())
        )
    }

    fun createNewUser(registrationUserDto: RegistrationUserDto): User {
        val user = User(
                username = registrationUserDto.username,
                password = passwordEncoder.encode(registrationUserDto.password),
                email = registrationUserDto.email,
                roles = listOf(roleService.getUserRole())
        )
        return userRepository.save(user)
    }
}