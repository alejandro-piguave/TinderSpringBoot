package com.example.tinderclonebackend.persistence.specs

import com.example.tinderclonebackend.persistence.entity.Gender
import com.example.tinderclonebackend.persistence.entity.Preference
import com.example.tinderclonebackend.persistence.entity.User
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

fun genderEquals(gender: Gender?): Specification<User>{
    return Specification<User> { root, _, criteriaBuilder ->
        if(gender == null) criteriaBuilder.conjunction()
        else criteriaBuilder.equal(root.get<Gender>("gender"), gender)
    }
}

fun preferenceEquals(preference: Preference): Specification<User>{
    return Specification<User> { root, _, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Preference>("preference"), preference)
    }
}

fun birthdateBetween(lowerBound: LocalDate, upperBound: LocalDate): Specification<User>{
    return Specification<User>{ root, _, criteriaBuilder ->
        criteriaBuilder.between(root.get("birthdate"), lowerBound, upperBound)
    }
}

fun withAgeWithinBounds(age: Int): Specification<User>{
    return Specification<User>{ root, _, criteriaBuilder ->
        criteriaBuilder.lessThanOrEqualTo(root.get("lowerAgeBound"), age)
    }.and { root, _, criteriaBuilder ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("upperAgeBound"), age)
    }
}
