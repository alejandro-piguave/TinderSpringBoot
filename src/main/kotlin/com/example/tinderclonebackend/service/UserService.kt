package com.example.tinderclonebackend.service

import com.example.tinderclonebackend.model.MessageModel
import com.example.tinderclonebackend.entity.User
import com.example.tinderclonebackend.model.CreateUserForm
import com.example.tinderclonebackend.model.EditUserForm
import com.example.tinderclonebackend.model.MatchModel
import com.example.tinderclonebackend.repository.MatchRepository
import com.example.tinderclonebackend.repository.MessageRepository
import com.example.tinderclonebackend.repository.SwipeRepository
import com.example.tinderclonebackend.repository.UserRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val swipeRepository: SwipeRepository,
                  private val matchRepository: MatchRepository,
                  private val messageRepository: MessageRepository){

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

    fun getMatches(userId: String): List<MatchModel>{
        return matchRepository.getMatches(userId).map { it.toModel() }
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

    fun sendMessage(userId: String, matchId: Long, message: String){
        val match = matchRepository.findById(matchId)
        match.ifPresentOrElse({
            if (it.matchedUser.id != userId && it.matchingUser.id != userId)
                throw AccessDeniedException("User doesn't have permission to access this match")
            messageRepository.save(matchId, userId, message)
        }, {
            throw IllegalArgumentException("No match with such id exists")
        })
    }

    fun getMessages(userId: String, matchId: Long): List<MessageModel> {
        val match = matchRepository.findById(matchId)

        if(match.isEmpty)
            throw IllegalArgumentException("No match with such id exists")

        val matchEntity = match.get()

        if (matchEntity.matchedUser.id != userId && matchEntity.matchingUser.id != userId)
            throw AccessDeniedException("User doesn't have permission to access this match")

        return matchEntity.messages.map { it.toModel(userId) }
    }
}