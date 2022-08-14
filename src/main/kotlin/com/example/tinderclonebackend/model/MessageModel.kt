package com.example.tinderclonebackend.model

import java.sql.Timestamp

data class MessageModel(val text: String, val timestamp: Timestamp, val isOwnMessage: Boolean)
