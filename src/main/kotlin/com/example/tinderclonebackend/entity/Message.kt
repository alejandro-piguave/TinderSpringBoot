package com.example.tinderclonebackend.entity

import com.example.tinderclonebackend.controller.response.MessageResponse
import java.sql.Timestamp
import javax.persistence.*

@Entity
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    val match: Match,
    @OneToOne
    val sender: User,
    val text: String,
    val timestamp: Timestamp
){
    fun toModel(userId: String): MessageResponse {
        return MessageResponse(text, timestamp, sender.id == userId)
    }
}
