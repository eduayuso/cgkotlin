package dev.eduayuso.cgkotlin.shared.domain.entities

/**
 * A line equation: "y = m*x + c", where 'm' is the slope and 'c' is the yIntercept
 */
data class LineEntity(

    val slope: Float,
    val yIntercept: Float
)