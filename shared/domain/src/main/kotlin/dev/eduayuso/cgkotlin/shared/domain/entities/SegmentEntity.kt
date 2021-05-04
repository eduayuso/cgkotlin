package dev.eduayuso.cgkotlin.shared.domain.entities

data class SegmentEntity(

    val a: PointEntity,
    val b: PointEntity
) {

    var value: Float = 0f

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

    /**
     * Used in Sweep Line algorithm
     */
    fun calculateValue(linePosition: Float) {

        val x1 = this.a.x
        val x2 = this.b.x
        val y1 = this.a.y
        val y2 = this.b.y
        this.value = y1 + (y2 - y1) / (x2 - x1) * (linePosition - x1)
    }
}

