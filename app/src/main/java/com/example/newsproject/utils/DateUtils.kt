package com.example.newsproject.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat

object DateUtils {
 // 2023-02-13T12:00:05Z
    const val isoDateType = "yyyy-MM-dd'T'HH:mm:ss"
    const val defaultDateType = "dd.MM.yyyy"
    //private val calendar = Calendar.getInstance()

    fun toDefaultDate(text: String): String? {
        val from = SimpleDateFormat(isoDateType)
        val to = SimpleDateFormat(defaultDateType)
        val date = from.parse(text)
        return to.format(date)
    }
}