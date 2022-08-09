package com.example.tinderclonebackend.entity

import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import javax.persistence.*

@Entity
class Swipe (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "swiping_user_id")
    val swipingUser: User,
    @ManyToOne
    @JoinColumn(name = "swiped_user_id")
    val swipedUser: User,
    val isLike: Boolean,
    @CreationTimestamp
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis())
)