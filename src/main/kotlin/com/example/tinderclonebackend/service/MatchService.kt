package com.example.tinderclonebackend.service

import com.example.tinderclonebackend.controller.response.MatchResponse
import com.example.tinderclonebackend.controller.response.MessageResponse
import com.example.tinderclonebackend.repository.MatchRepository
import com.example.tinderclonebackend.repository.MessageRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository,
                   private val messageRepository: MessageRepository){

    fun getMatches(userId: String): List<MatchResponse>{
        return matchRepository.getMatches(userId).map { it.toModel() }
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

    fun getMessages(userId: String, matchId: Long): List<MessageResponse> {
        val match = matchRepository.findById(matchId)

        if(match.isEmpty)
            throw IllegalArgumentException("No match with such id exists")

        val matchEntity = match.get()

        if (matchEntity.matchedUser.id != userId && matchEntity.matchingUser.id != userId)
            throw AccessDeniedException("User doesn't have permission to access this match")

        return matchEntity.messages.map { it.toModel(userId) }
    }
}