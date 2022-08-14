package com.example.tinderclonebackend.model

import java.sql.Timestamp

data class MatchModel(val id: Long, val timestamp: Timestamp, val name: String, val age: Int, val bio: String)