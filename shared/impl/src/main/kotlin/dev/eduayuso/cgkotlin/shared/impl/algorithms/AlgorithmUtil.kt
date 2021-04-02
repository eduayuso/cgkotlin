package dev.eduayuso.cgkotlin.shared.impl.algorithms

import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity

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
}