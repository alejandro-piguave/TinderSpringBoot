package com.example.tinderclonebackend.entity

import com.example.tinderclonebackend.controller.response.MatchResponse
import org.hibernate.type.LocalDateType
import java.sql.Timestamp
import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@NamedNativeQuery(name = "MatchResultQuery",
    query = "select m.id as id, m.timestamp as timestampString, u.name as name, u.bio as bio, u.birthdate as birthDate from `match` m inner join user u on u.id = IF(m.matching_user_id = :user_id, m.matched_user_id, m.matching_user_id) where m.matched_user_id = :user_id or m.matching_user_id = :user_id",
    resultSetMapping ="MatchResultMapping")
@SqlResultSetMapping(name = "MatchResultMapping",
    classes = [
        ConstructorResult(targetClass = MatchResult::class,
            columns = [
                ColumnResult(name = "id", type = Long::class),
                ColumnResult(name = "timestampString", type= String::class),
                ColumnResult(name = "name", type = String::class),
                ColumnResult(name = "bio", type = String::class),
                ColumnResult(name = "birthDate", type = LocalDateType::class)
            ])]
)
@Entity
class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "matching_user_id")
    val matchingUser: User,
    @ManyToOne
    @JoinColumn(name = "matched_user_id")
    val matchedUser: User,
    val timestamp: Timestamp,
    @OneToMany(mappedBy = "match",cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("timestamp")
    val messages: List<Message> = listOf()
)

class MatchResult(
    private val id: Long,
    timestampString: String,
    private val name: String,
    private val bio: String,
    private val birthDate: LocalDate
){
    private val timestamp:Timestamp = Timestamp.valueOf(timestampString)

    fun toModel(): MatchResponse {
        val now = LocalDate.now()
        val age = Period.between(birthDate,now).years
        return MatchResponse(id, timestamp, name, age ,bio)
    }
}