package com.example.tinderclonebackend.repository

import com.example.tinderclonebackend.entity.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface UserRepository: CrudRepository<User, String> {

    @Modifying
    @Transactional
    @Query(
        value = "insert into user_likes_user (like_from, like_to) values (:like_from, :like_to)",
        nativeQuery = true
    )
    fun likeUser(
        @Param("like_from") likeFrom: String, @Param("like_to") likeTo: String
    )

    @Modifying
    @Transactional
    @Query(
        value = "insert into user_dislikes_user (dislike_from, dislike_to) values (:dislike_from, :dislike_to)",
        nativeQuery = true
    )
    fun passUser(
        @Param("dislike_from") dislikeFrom: String, @Param("dislike_to") dislikeTo: String
    )
}