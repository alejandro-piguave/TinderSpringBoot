package com.example.tinderclonebackend.controller

import com.example.tinderclonebackend.model.*
import com.example.tinderclonebackend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
@RestController
class UserController(private val userService: UserService) {

    @PostMapping("users")
    fun createUser(@RequestBody createUserForm: CreateUserForm): ResponseEntity<String> {
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.saveUser(uid, createUserForm)
        return ResponseEntity.ok("User created")
    }

    @PutMapping("users")
    fun editProfile(@RequestBody editUserForm: EditUserForm): ResponseEntity<String>{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.updateUser(uid, editUserForm)
        return ResponseEntity.ok("User updated")
    }

    @PutMapping("users/{userId}/like")
    @ResponseBody
    fun likeUser(@PathVariable userId: String): UserLikedResponse{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        val isMatch = userService.likeUser(uid, userId)
        return UserLikedResponse(isMatch)
    }

    @PutMapping("users/{userId}/pass")
    fun passUser(@PathVariable userId: String): ResponseEntity<String>{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.passUser(uid, userId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("matches")
    @ResponseBody
    fun getMatches(): List<MatchModel>{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        return userService.getMatches(uid)
    }

    @GetMapping("matches/{matchId}/messages")
    @ResponseBody
    fun getMessages(@PathVariable matchId: Long): List<MessageModel> {
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        return userService.getMessages(uid, matchId)
    }

    @PostMapping("matches/{matchId}/messages")
    @ResponseBody
    fun sendMessage(@PathVariable matchId: Long, @RequestBody createMessageForm: CreateMessageForm): ResponseEntity<String> {
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.sendMessage(uid, matchId, createMessageForm.message)
        return ResponseEntity.ok("Message created")
    }



}