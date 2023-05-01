package com.example.tinderclonebackend.service

import com.example.tinderclonebackend.controller.request.CreateUserRequest
import com.example.tinderclonebackend.controller.request.EditUserRequest
import com.example.tinderclonebackend.controller.response.UserResponse
import com.example.tinderclonebackend.persistence.entity.Gender
import com.example.tinderclonebackend.persistence.entity.Preference
import com.example.tinderclonebackend.persistence.repository.MatchRepository
import com.example.tinderclonebackend.persistence.repository.SwipeRepository
import com.example.tinderclonebackend.persistence.repository.UserRepository
import com.example.tinderclonebackend.persistence.specs.birthdateBetween
import com.example.tinderclonebackend.persistence.specs.genderEquals
import com.example.tinderclonebackend.persistence.specs.preferenceEquals
import com.example.tinderclonebackend.persistence.specs.withAgeWithinBounds
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

@Service
class UserService(private val userRepository: UserRepository,
                  private val swipeRepository: SwipeRepository,
                  private val matchRepository: MatchRepository
){

    fun saveUser(uid: String, form: CreateUserRequest){
        userRepository.save(form.toEntity(uid))
    }

    fun updateUser(uid: String, request: EditUserRequest){
        val repositoryUser = userRepository.findById(uid)

        repositoryUser.ifPresent {
            request.editUser(it)
            userRepository.save(it)
        }
    }

    fun getUsers(userId: String): List<UserResponse>{
        val userOptional = userRepository.findById(userId)
        if(userOptional.isEmpty) throw IllegalArgumentException("User doesn't exist")
        val user = userOptional.get()

        val genderSpecification = genderEquals(
            when (user.preference) {
                Preference.MEN -> Gender.MALE
                Preference.WOMEN -> Gender.FEMALE
                else -> null
            }
        )

        val preferenceSpecification = preferenceEquals(
            when(user.gender){
                Gender.MALE -> Preference.MEN
                Gender.FEMALE -> Preference.WOMEN
            }
        ).or(preferenceEquals(Preference.BOTH))

        val lowerBirthdateBound = LocalDate.now().minusYears(user.lowerAgeBound.toLong())
        val upperBirthdateBound = LocalDate.now().minusYears(user.upperAgeBound.toLong())

        val ageSpecification = birthdateBetween(lowerBirthdateBound, upperBirthdateBound)


        val userAge = Period.between(user.birthdate, LocalDate.now()).years
        val ageRangeSpecification = withAgeWithinBounds(userAge)

        val specifications = Specification
            .where(genderSpecification)
            .and(preferenceSpecification)
            .and(ageSpecification)
            .and(ageRangeSpecification)

        val users = userRepository.findAll(specifications)

        return users.map { UserResponse(it) }
    }

    fun likeUser(uid: String, swipedUserId: String): Boolean{
        if(uid == swipedUserId) throw IllegalArgumentException("Users can't swipe themselves")
        return try{
            swipeRepository.likeUser(uid,swipedUserId)
            val isMatch = swipeRepository.existsLike(swipedUserId, uid)
            if (isMatch){
                matchRepository.match(uid, swipedUserId)
            }
            isMatch
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