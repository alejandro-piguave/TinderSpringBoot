package com.example.tinderclonebackend.utils

import java.time.LocalDate
import java.time.format.DateTimeParseException

fun parseOrNull(text: CharSequence ): LocalDate?{
    return try {
        LocalDate.parse(text)
    }catch (e: DateTimeParseException){
        null
    }
}

inline fun <reified T : Enum<*>> enumValueOrNull(name: String): T? =
    T::class.java.enumConstants.firstOrNull { it.name == name.uppercase() }

fun <T> Array<T>.toListString(): String = toList().toString().replace("[","").replace("]","")