package com.example.tinderclonebackend.controller.response

import java.sql.Timestamp

data class MessageResponse(val text: String, val timestamp: Timestamp, val isOwnMessage: Boolean)
