package com.example.tinderclonebackend.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.annotation.PostConstruct

@Configuration
class FirebaseConfig {

    @PostConstruct
    fun init(){
        val resource = ClassPathResource("tindercloneflutter-f801e-firebase-adminsdk-i78z5-a0e08fb180.json")
        val refreshToken = resource.inputStream

        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(refreshToken))
            //.setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
            .build()

        FirebaseApp.initializeApp(options)
    }
}