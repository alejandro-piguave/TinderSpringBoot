package com.example.tinderclonebackend.service

import com.example.tinderclonebackend.controller.request.CreateUserRequest
import com.example.tinderclonebackend.controller.request.EditUserRequest
import com.example.tinderclonebackend.entity.User
import com.example.tinderclonebackend.repository.MatchRepository
import com.example.tinderclonebackend.repository.MessageRepository
import com.example.tinderclonebackend.repository.SwipeRepository
import com.example.tinderclonebackend.repository.UserRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val swipeRepository: SwipeRepository,
                  private val matchRepository: MatchRepository){

    fun saveUser(uid: String, form: CreateUserRequest){
        userRepository.save(form.toEntity(uid))
    }

    fun updateUser(uid: String, form: EditUserRequest){
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
        if(uid == swipedUserId) throw IllegalArgumentException("Users can't swipe themselves")
        return try{
            swipeRepository.likeUser(uid,swipedUserId)
            val likesBack = swipeRepository.existsLike(swipedUserId, uid)
            if (likesBack){
                matchRepository.match(uid, swipedUserId)
            }
            likesBack
        }catch (e: DataIntegrityViolationException){
            handleSQLException(e)
            false
        }
    }

    private fun handleSQLException(e: DataIntegrityViolationException){
        when ((e.cause as? ConstraintViolationException)?.sqlException?.errorCode ?: 0) {
            1452 -> throw IllegalArgumentException("User with such id doesn't exist")
            1062 -> throw IllegalArgumentException("User has already been swiped")
            else -> throw IllegalArgumentException("There was an error while processing the swipe")
        }
    }

    fun passUser(uid: String, swipedUserId: String){
        if(uid == swipedUserId) throw IllegalArgumentException("Users can't swipe themselves")
        try{
            swipeRepository.dislikeUser(uid, swipedUserId)
        }catch (e: DataIntegrityViolationException){
            handleSQLException(e)
        }
    }
}