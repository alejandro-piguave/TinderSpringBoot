package com.example.tinderclonebackend.controller.response

import java.sql.Timestamp

data class MatchResponse(val id: Long, val timestamp: Timestamp, val name: String, val age: Int, val bio: String)