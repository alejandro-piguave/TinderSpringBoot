package com.example.tinderclonebackend.persistence.repository

import com.example.tinderclonebackend.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, String>, JpaSpecificationExecutor<User>