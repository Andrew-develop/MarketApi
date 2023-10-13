package com.example.demo.services

import com.example.demo.entities.Role
import com.example.demo.repositories.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService (
        private val roleRepository: RoleRepository
) {
    fun getUserRole(): Role {
        return roleRepository.findByName(name = "ROLE_USER")
    }
}