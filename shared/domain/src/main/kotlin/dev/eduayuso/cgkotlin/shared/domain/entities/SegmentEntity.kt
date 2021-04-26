package dev.eduayuso.cgkotlin.shared.domain.entities

import kotlin.math.abs

data class SegmentEntity(

    val a: PointEntity,
    val b: PointEntity
) {
    /**
     * Line equation: y = m*x + c
     */
    fun toLine(): LineEntity {

        val slope = (b.y - a.y) / (b.x - a.x)
        val yIntercept = a.y - slope * a.x;
        return LineEntity(slope, yIntercept)
    }

    /**
     * Check if point coordinates is the bounding box defined by the segment
     */
    fun isPointInsideBox(i: PointEntity): Boolean {

        val left = if (a.x < b.x) a.x else b.x
        val right = if (left == a.x) b.x else a.x
        val lower = if (a.y < b.y) a.y else b.y
        val upper = if (lower == a.y) b.y else a.y

        return  i.x in left..right && i.y in lower..upper
    }
}

