package com.example.tinderclonebackend.security.provider

import com.example.tinderclonebackend.model.FirebaseToken
import com.google.firebase.auth.AuthErrorCode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class FirebaseAuthenticationProvider: AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication? {
        val tokenString = authentication?.principal.toString()

        //Code for testing
        /*return if(tokenString == "123456")
            FirebaseToken("alex_id")
        else null*/

        return try {
            val checkRevoked = true
            val decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenString, checkRevoked)
            val uid = decodedToken.uid
            FirebaseToken(uid)
        } catch (e: FirebaseAuthException) {
            null
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == FirebaseToken::class.java
    }
}