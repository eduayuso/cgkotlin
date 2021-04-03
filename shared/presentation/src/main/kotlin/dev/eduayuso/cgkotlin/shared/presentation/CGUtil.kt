package dev.eduayuso.cgkotlin.shared.presentation

object CGUtil {

    fun parseTime(millis: Long): String {

        val seconds = (millis / 1000).toInt() % 60
        val minutes = (millis / (1000 * 60) % 60).toInt()
        val hours = (millis / (1000 * 60 * 60) % 24).toInt()

        return "$minutes min $seconds secs"
    }
}