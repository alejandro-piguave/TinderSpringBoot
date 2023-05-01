package com.example.tinderclonebackend.persistence.entity

import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

@Embeddable
class SwipeKey(
    @Column(name = "swiping_user_id")
    val swipingUserId: String,
    @Column(name = "swiped_user_id")
    val swipedUserId: String): Serializable

@Entity
class Swipe (
    @EmbeddedId
    val swipeKey: SwipeKey,
    @ManyToOne
    @MapsId("swipingUserId")
    @JoinColumn(name = "swiping_user_id")
    val swipingUser: User,
    @ManyToOne
    @MapsId("swipedUserId")
    @JoinColumn(name = "swiped_user_id")
    val swipedUser: User,
    val isLike: Boolean,
    val timestamp: Timestamp
)