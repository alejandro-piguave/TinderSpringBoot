package com.example.tinderclonebackend.repository

import com.example.tinderclonebackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, String>