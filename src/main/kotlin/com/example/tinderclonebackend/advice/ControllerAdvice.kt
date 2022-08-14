package com.example.tinderclonebackend.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.security.access.AccessDeniedException

@ControllerAdvice
class ControllerAdvice{

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseBody
    fun onException(ex: IllegalArgumentException): ResponseEntity<Any> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(value = [AccessDeniedException::class])
    @ResponseBody
    fun onException(ex: AccessDeniedException): ResponseEntity<Any> {
        return ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)
    }
}