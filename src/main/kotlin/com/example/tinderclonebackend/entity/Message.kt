package com.example.tinderclonebackend.entity

import com.example.tinderclonebackend.model.MessageModel
import java.sql.Timestamp
import javax.persistence.*

@Entity
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    val match: Match,
    val senderId: String,
    val text: String,
    val timestamp: Timestamp
){
    fun toModel(userId: String): MessageModel{
        return MessageModel(text, timestamp, senderId == userId)
    }
}
