package com.example.tinderclonebackend.controller

import com.example.tinderclonebackend.model.EditUserForm
import com.example.tinderclonebackend.model.CreateUserForm
import com.example.tinderclonebackend.model.UserLikedResponse
import com.example.tinderclonebackend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
@RestController
class UserController(private val userService: UserService) {


    @PostMapping("user")
    fun createUser(@RequestBody createUserForm: CreateUserForm): ResponseEntity<String> {
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.saveUser(uid, createUserForm)
        return ResponseEntity.ok("User created")
    }

    @PutMapping("user")
    fun editProfile(@RequestBody editUserForm: EditUserForm): ResponseEntity<String>{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.updateUser(uid, editUserForm)
        return ResponseEntity.ok("User updated")
    }

    @PostMapping("likeUser")
    @ResponseBody
    fun likeUser(@RequestParam userId: String): UserLikedResponse{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        val isMatch = userService.likeUser(uid, userId)
        return UserLikedResponse(isMatch)
    }

    @PostMapping("passUser")
    fun passUser(@RequestParam userId: String): ResponseEntity<String>{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        userService.passUser(uid, userId)
        return ResponseEntity.ok().build()
    }

}