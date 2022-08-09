package com.example.tinderclonebackend.repository;

import com.example.tinderclonebackend.entity.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.Temporal
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import javax.persistence.TemporalType

@Repository
interface MatchRepository : JpaRepository<Match, Long> {

    @Modifying
    @Transactional
    @Query(
        value = "insert into `match` (matched_user_id, matching_user_id, timestamp) values (:match_from, :match_to, current_timestamp)",
        nativeQuery = true
    )
    fun match(
        @Param("match_from") matchFrom: String, @Param("match_to") matchTo: String
    )
}