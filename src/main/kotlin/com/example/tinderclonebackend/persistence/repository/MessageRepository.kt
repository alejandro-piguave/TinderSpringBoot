package com.example.tinderclonebackend.persistence.repository

import com.example.tinderclonebackend.persistence.entity.Message
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface MessageRepository: CrudRepository<Message, Long>{
    @Modifying
    @Transactional
    @Query(
        value = "insert into message (sender_id, text, timestamp, match_id) values (:sender_id, :text, current_timestamp, :match_id)",
        nativeQuery = true
    )
    fun save(
        @Param("match_id") matchId: Long, @Param("sender_id") senderId: String, @Param("text") text: String
    )
}