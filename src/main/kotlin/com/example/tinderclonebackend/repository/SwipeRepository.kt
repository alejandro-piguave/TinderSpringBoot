package com.example.tinderclonebackend.repository

import com.example.tinderclonebackend.entity.Swipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SwipeRepository: JpaRepository<Swipe, Long> {

    @Query(
        value = "select IF(count(*) > 0, 'true', 'false') from swipe s where s.swiping_user_id = :like_from and s.swiped_user_id = :like_to and s.is_like = true",
        nativeQuery = true
    )
    fun existsLike(
        @Param("like_from") likeFrom: String, @Param("like_to") likeTo: String
    ) : Boolean


    @Modifying
    @Transactional
    @Query(
        value = "insert into swipe (swiping_user_id, swiped_user_id, is_like, timestamp) values (:like_from, :like_to, true, current_timestamp)",
        nativeQuery = true
    )
    fun likeUser(
        @Param("like_from") likeFrom: String, @Param("like_to") likeTo: String
    )

    @Modifying
    @Transactional
    @Query(
        value = "insert into swipe (swiping_user_id, swiped_user_id, is_like, timestamp) values (:like_from, :like_to, false, current_timestamp)",
        nativeQuery = true
    )
    fun dislikeUser(
        @Param("like_from") dislikeFrom: String, @Param("like_to") dislikeTo: String
    )
}