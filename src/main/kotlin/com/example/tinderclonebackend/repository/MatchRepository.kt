package com.example.tinderclonebackend.repository;

import com.example.tinderclonebackend.entity.Match
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository : JpaRepository<Match, Long> {
}