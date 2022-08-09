package com.example.tinderclonebackend.repository

import com.example.tinderclonebackend.entity.Message
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: CrudRepository<Message, Long>