package com.example.tinderclonebackend.controller.request

import com.example.tinderclonebackend.persistence.entity.Gender
import com.example.tinderclonebackend.persistence.entity.Preference
import com.example.tinderclonebackend.persistence.entity.User
import com.example.tinderclonebackend.utils.enumValueOrNull
import com.example.tinderclonebackend.utils.toListString

data class EditUserRequest(
    val bio: String?,
    val gender: String?,
    val preference: String?,
    val lowerAgeBound: Int?,
    val upperAgeBound: Int?,
){
    fun editUser(user: User){
        if(gender != null){
            val userGender = enumValueOrNull<Gender>(gender) ?: throw IllegalArgumentException("Field 'gender' must be one of the following values: ${Gender.values().toListString()}")
            if(userGender != user.gender)
                user.gender = userGender
        }
        if(preference != null){
            val userPreference = enumValueOrNull<Preference>(preference) ?: throw IllegalArgumentException("Field 'preference' must be one of the following values: ${Preference.values().toListString()}")
            if(userPreference != user.preference)
                user.preference = userPreference
        }
        if(bio != null && user.bio != bio)
            user.bio = bio
        if(lowerAgeBound != null && user.lowerAgeBound != lowerAgeBound)
            user.lowerAgeBound = lowerAgeBound
        if(upperAgeBound != null && user.upperAgeBound != upperAgeBound)
            user.upperAgeBound = upperAgeBound
    }
}


