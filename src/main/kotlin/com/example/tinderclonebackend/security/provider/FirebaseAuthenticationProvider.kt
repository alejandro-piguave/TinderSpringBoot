package com.example.tinderclonebackend.security.provider

import com.example.tinderclonebackend.model.FirebaseToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class FirebaseAuthenticationProvider: AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication? {
        val tokenString = authentication!!.principal.toString()
        return if(tokenString == "123456")
            FirebaseToken("defaultUserId")
        else null
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == FirebaseToken::class.java
    }
}