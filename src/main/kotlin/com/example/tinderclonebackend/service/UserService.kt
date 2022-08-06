package com.example.tinderclonebackend.service

import com.example.tinderclonebackend.entity.User
import com.example.tinderclonebackend.model.EditUserForm
import com.example.tinderclonebackend.model.CreateUserForm
import com.example.tinderclonebackend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository){
    fun saveUser(uid: String, form: CreateUserForm){
        userRepository.save(form.toEntity(uid))
    }

    fun updateUser(uid: String, form: EditUserForm){
        val repositoryUser = userRepository.findById(uid)

        repositoryUser.ifPresent {
            println("User present in repository")
            form.editUser(it)
            userRepository.save(it)
        }
    }


    fun fetchCandidates(userId: String): List<User>{
        return listOf()
    }

    fun likeUser(uid: String, likeTo: String): Boolean{
        val exists = userRepository.existsById(likeTo)
        if(exists) userRepository.likeUser(uid, likeTo)
        return exists
    }

    fun passUser(uid: String, likeTo: String): Boolean{
        val exists = userRepository.existsById(likeTo)
        if(exists) userRepository.passUser(uid, likeTo)
        return exists
    }

    private fun createMatch(matchingUser: User, matchedUser: User){

    }

    fun sendMessage(){

    }
}