package com.example.tinderclonebackend.security.token

import org.springframework.security.authentication.AbstractAuthenticationToken

class FirebaseToken(private val token: String): AbstractAuthenticationToken(null) {
    override fun getCredentials(): Any? = null

    override fun getPrincipal(): String = token
}