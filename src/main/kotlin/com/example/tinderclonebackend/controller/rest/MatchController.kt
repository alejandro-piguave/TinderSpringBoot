package com.example.tinderclonebackend.controller.rest

import com.example.tinderclonebackend.controller.request.CreateMessageRequest
import com.example.tinderclonebackend.controller.response.MatchResponse
import com.example.tinderclonebackend.controller.response.MessageResponse
import com.example.tinderclonebackend.service.MatchService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class MatchController(private val matchService: MatchService) {

    @GetMapping("matches")
    @ResponseBody
    fun getMatches(): List<MatchResponse>{
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        return matchService.getMatches(uid)
    }

    @GetMapping("matches/{matchId}/messages")
    @ResponseBody
    fun getMessages(@PathVariable matchId: Long): List<MessageResponse> {
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        return matchService.getMessages(uid, matchId)
    }

    @PostMapping("matches/{matchId}/messages")
    @ResponseBody
    fun sendMessage(@PathVariable matchId: Long, @RequestBody createMessageRequest: CreateMessageRequest): ResponseEntity<String> {
        val uid = SecurityContextHolder.getContext().authentication.principal.toString()
        matchService.sendMessage(uid, matchId, createMessageRequest.message)
        return ResponseEntity.ok("Message created")
    }
}