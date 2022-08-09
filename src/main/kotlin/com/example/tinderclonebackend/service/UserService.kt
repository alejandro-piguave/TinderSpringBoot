package com.example.tinderclonebackend.service

import com.example.tinderclonebackend.entity.User
import com.example.tinderclonebackend.model.CreateUserForm
import com.example.tinderclonebackend.model.EditUserForm
import com.example.tinderclonebackend.repository.MatchRepository
import com.example.tinderclonebackend.repository.SwipeRepository
import com.example.tinderclonebackend.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserService(private val userRepository: UserRepository,
                  private val swipeRepository: SwipeRepository,
                  private val matchRepository: MatchRepository){

    fun saveUser(uid: String, form: CreateUserForm){
        userRepository.save(form.toEntity(uid))
    }

    fun updateUser(uid: String, form: EditUserForm){
        val repositoryUser = userRepository.findById(uid)

        repositoryUser.ifPresent {
            form.editUser(it)
            userRepository.save(it)
        }
    }

    fun fetchCandidates(userId: String): List<User>{
        return listOf()
    }

    fun likeUser(uid: String, swipedUserId: String): Boolean{
        try{
            swipeRepository.likeUser(uid,swipedUserId)
            val likesBack = swipeRepository.existsLike(swipedUserId, uid)
            if (likesBack){
                matchRepository.match(uid, swipedUserId)
            }
            return likesBack
        }catch (e: DataIntegrityViolationException){
            throw EntityNotFoundException("No user found with such id")
        }
    }

    fun passUser(uid: String, swipedUserId: String){
        try{
            swipeRepository.dislikeUser(uid, swipedUserId)
        }catch (e: DataIntegrityViolationException){
            throw EntityNotFoundException("No user found with such id")
        }
    }

    fun sendMessage(){

    }
}