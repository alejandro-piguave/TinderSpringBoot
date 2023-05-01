package com.example.tinderclonebackend.controller.response

import com.example.tinderclonebackend.persistence.entity.User
import java.time.LocalDate
import java.time.Period

class UserResponse(
    val id: String,
    var bio: String,
    var name: String,
    val age: Int
){
    constructor(user: User): this(
        user.id,
        user.bio,
        user.name,
        Period.between(user.birthdate, LocalDate.now()).years
    )
}