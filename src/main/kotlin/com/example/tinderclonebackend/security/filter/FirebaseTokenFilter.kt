package com.example.tinderclonebackend.security.filter

import com.example.tinderclonebackend.security.token.FirebaseToken
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FirebaseTokenFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw BadCredentialsException("Missing token.")
        }

        val idToken = authHeader.substring(7)

        val firebaseToken = FirebaseToken(idToken)
        SecurityContextHolder.getContext().authentication = firebaseToken
        /*
        try {
            //val checkRevoked = true
            //val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken, checkRevoked)
            //val uid = decodedToken.uid



        } catch (e: FirebaseAuthException) {
            if (e.authErrorCode == AuthErrorCode.REVOKED_ID_TOKEN) {

            } else {
            }
        }
*/
        filterChain.doFilter(request,response)
    }
}