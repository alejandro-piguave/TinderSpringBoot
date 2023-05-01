package com.example.tinderclonebackend.controller.rest

import com.example.tinderclonebackend.controller.request.CreateUserRequest
import com.example.tinderclonebackend.controller.request.EditUserRequest
import com.example.tinderclonebackend.controller.response.UserLikedResponse
import com.example.tinderclonebackend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
@RestController
class UserController(private val userService: UserService) {

    @PostMapping("users")
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<String> {
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.saveUser(uid, createUserRequest)
        return ResponseEntity.ok("User created")
    }

    @PutMapping("users")
    fun editProfile(@RequestBody editUserRequest: EditUserRequest): ResponseEntity<String>{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.updateUser(uid, editUserRequest)
        return ResponseEntity.ok("User updated")
    }

    @PutMapping("users/{userId}/like")
    @ResponseBody
    fun likeUser(@PathVariable userId: String): UserLikedResponse {
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

}