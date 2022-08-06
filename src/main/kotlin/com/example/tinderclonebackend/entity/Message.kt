package com.example.tinderclonebackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Timestamp
import javax.persistence.*

@Entity
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    val match: Match,
    val senderId: String,
    val text: String,
    val timestamp: Timestamp
)
