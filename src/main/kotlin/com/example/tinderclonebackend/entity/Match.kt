package com.example.tinderclonebackend.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "matching_user_id")
    val matchingUser: User,
    @ManyToOne
    @JoinColumn(name = "matched_user_id")
    val matchedUser: User,
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
    @OneToMany(mappedBy = "match",cascade = [CascadeType.ALL], orphanRemoval = true)
    val messages: List<Message> = listOf()
)