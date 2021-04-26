package dev.eduayuso.cgkotlin.shared.impl.algorithms

import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentEntity

object AlgorithmUtil {

    /**
     * Checks if point s is at left of vector p->q
     */
    fun toLeft(p: PointEntity, q: PointEntity, s: PointEntity) =

        (p.x * q.y - p.y * q.x +
         q.x * s.y - q.y * s.x +
         s.x * p.y - s.y * p.x) > 0

    fun toLeft(points: PointSetEntity, p: Int, q: Int, s: Int) =
        toLeft(points.list[p], points.list[q], points.list[s])

    /**
     * Returns the lower and left most point index of the set
     */
    fun lowerThenLeftMost(points: PointSetEntity): Int {

        val n = points.list.size
        var ltl = 0

        for (s in 0 until n) {

            val ltlp = points.list[ltl]
            val sp   = points.list[s]
            if (sp.y < ltlp.y || sp.y == ltlp.y && sp.x <= ltlp.x) {
                ltl = s
            }
        }

        return ltl
    }

    /**
     * Returns 1 if polar angle between p0 and a is greater than between p0 and b, else -1
     */
    fun polarAngleComparator(p0: PointEntity, a: PointEntity, b: PointEntity): Int {

    /*    val deltaY = kotlin.math.abs(a.y - b.y)
        val deltaX = kotlin.math.abs(a.x - b.x)
        return Math.toDegrees(kotlin.math.atan2(deltaY.toDouble(), deltaX.toDouble()))*/

        val cotanA = -(a.x - p0.x) / (a.y - p0.y)
        val cotanB = -(b.x - p0.x) / (b.y - p0.y)
        return if (cotanA - cotanB < 0) 1
        else -1
    }

    /**
     * ------------------------------------------------------------------------------------
     * First we find the lines intersection according to the formula:
     * LineP => y1 = m1*x1 + c1
     * LineQ => y2 = m2*x2 + c2
     * The point x,y where intersects is the one who assert this:
     * y1 = y2 and x1 = x2
     * ------------------------------------------------------------------------------------
     * Second, we check if the intersection point is inside the two segments
     */
    fun findIntersection(p: SegmentEntity, q: SegmentEntity): PointEntity? {

        /**
         * First we find the lines intersection
         */
        val lineP = p.toLine()
        val lineQ = q.toLine()

        val x = (lineQ.yIntercept - lineP.yIntercept) / (lineP.slope - lineQ.slope)
        val y = lineP.slope * x + lineP.yIntercept

        /**
         * Second, we check if the intersection point is inside the two segments
         */
        val i = PointEntity(x, y)
        if (p.isPointInsideBox(i) && q.isPointInsideBox(i)) {
            return i
        } else {
            return null
        }
    }
}