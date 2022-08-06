package com.example.tinderclonebackend.model

import com.example.tinderclonebackend.entity.Gender
import com.example.tinderclonebackend.entity.Preference
import com.example.tinderclonebackend.entity.User
import com.example.tinderclonebackend.utils.enumValueOrNull
import com.example.tinderclonebackend.utils.parseOrNull
import com.example.tinderclonebackend.utils.toListString

data class CreateUserForm(
    val email: String?,
    val name: String?,
    val birthDate: String?,
    val bio: String?= null,
    val gender: String?,
    val preference: String?
){
    fun toEntity(uid: String): User{
        val userEmail = email ?: throw IllegalArgumentException("Field 'email' can't be null")
        val userName = name ?: throw IllegalArgumentException("Field 'name' can't be null")
        val userBio = bio ?: ""
        val userBirthDateString = birthDate ?: throw IllegalArgumentException("Field 'birthDate' can't be null")
        val userBirthDate = parseOrNull(userBirthDateString) ?: throw IllegalArgumentException("Field 'birthDate' must conform to yyyy-MM-dd format")
        val userGenderString = gender?: throw IllegalArgumentException("Field 'gender' can't be null")
        val userGender = enumValueOrNull<Gender>(userGenderString) ?: throw IllegalArgumentException("Field 'gender' must be one of the following values: ${Gender.values().toListString()}")
        val userPreferenceString = preference?: throw IllegalArgumentException("Field 'preference' can't be null")
        val userPreference = enumValueOrNull<Preference>(userPreferenceString) ?: throw IllegalArgumentException("Field 'gender' must be one of the following values: ${Preference.values().toListString()}")

        return User(uid,userBio,userName,userBirthDate,userGender,userPreference)
    }
}
