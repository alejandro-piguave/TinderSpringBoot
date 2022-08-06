package com.example.tinderclonebackend.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,
    @ManyToOne
    @MapsId("matchingUserId")
    @JoinColumn(name = "matching_user_id")
    val matchingUser: User,
    @ManyToOne
    @MapsId("matchedUserId")
    @JoinColumn(name = "matched_user_id")
    val matchedUser: User,
    val timestamp: Timestamp,
    @OneToMany(mappedBy = "match",cascade = [CascadeType.ALL], orphanRemoval = true)
    val messages: List<Message> = listOf()
)