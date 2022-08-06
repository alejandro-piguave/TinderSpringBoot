package com.example.tinderclonebackend.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*

@Entity
class User(@Id val id: String,
                var bio: String = "",
                var name: String = "",
                var birthdate: LocalDate,
                @Enumerated(EnumType.STRING)
                var gender: Gender,
                @Enumerated(EnumType.STRING)
                var preference: Preference,
                var lowerAgeBound: Int = 18,
                var upperAgeBound: Int = 30,
                @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
                @JoinTable(name = "user_likes_user",
                    joinColumns = [JoinColumn(name = "like_from", referencedColumnName = "id")],
                    inverseJoinColumns = [JoinColumn(name="like_to", referencedColumnName = "id")])
                @JsonIgnore
                val likesSent: List<User> = listOf(),
                @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likesSent")
                @JsonIgnore
                val likesReceived: List<User> = listOf(),
                @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
                @JoinTable(name = "user_dislikes_user",
                    joinColumns = [JoinColumn(name = "dislike_from", referencedColumnName = "id")],
                    inverseJoinColumns = [JoinColumn(name="dislike_to", referencedColumnName = "id")])
                @JsonIgnore
                val dislikesSent: List<User> = listOf(),
                @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dislikesSent")
                @JsonIgnore
                val dislikesReceived: List<User> = listOf(),
                @OneToMany(mappedBy = "matchedUser", fetch = FetchType.LAZY)
                @JsonIgnore
                val matchingUsers: List<Match> = listOf(),
                @OneToMany(mappedBy = "matchingUser", fetch = FetchType.LAZY)
                @JsonIgnore
                val matchedUsers: List<Match> = listOf()
                ){

    val matches: List<Match>
        get() = matchedUsers + matchingUsers

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false

        other as User
        if(id != other.id) return false
        if(name != other.name) return false
        if(birthdate != other.birthdate) return false
        if(gender != other.gender) return false
        if(preference != other.preference) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + bio.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + birthdate.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + preference.hashCode()
        return result
    }
}


enum class Preference{MEN, WOMEN, BOTH}
enum class Gender{MALE, FEMALE}