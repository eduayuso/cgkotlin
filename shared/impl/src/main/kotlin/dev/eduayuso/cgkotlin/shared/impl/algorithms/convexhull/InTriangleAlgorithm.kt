package dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.impl.algorithms.AlgorithmUtil

object InTriangleAlgorithm: IConvexHullAlgorithm {

    override val stepOption = true

    private var p = 0
    private var q = 0
    private var r = 0

    override fun run(input: PointSetEntity, listener: IConvexHullTaskListener) {

        val points = input.list
        val n = input.list.size-1

        listener.onStart(input)

        points.forEach { it.isExtreme = true }

        for (p in 0..n) { // O(n)

            for (q in p + 1..n) { // O(n)

                for (r in q + 1..n) { // O(n)

                    for (s in 0..n) { // O(n)

                        if (s == p || s == q || s == r || !points[s].isExtreme) continue
                        if (inTriangle(input.list, p, q, r, s)) {
                            points[s].isExtreme = false
                        }
                    }
                }
            }

            listener.onStep(null, null)
        }

        listener.onFinish(input)
    }

    override fun step(input: PointSetEntity, listener: IConvexHullTaskListener) {

        val points = input.list
        val n = points.size

        if (p == 0 && q == 0 && r == 0) {
            q = 1
            r = 2
            points.forEach { it.isExtreme = true }
            listener.onStart(input)
        }

        for (s in 0 until n) {

            if (s == p || s == q || s == r || !points[s].isExtreme) continue
            if (inTriangle(input.list, p, q, r, s) || inTriangle(input.list, p, r, q, s)) {
                points[s].isExtreme = false
            }
        }

        /**
         * Indexes for the next iteration
         */
        if (r == n-1) {
            r = ++q
            if (q == n-1) {
                q = ++p
                if (p == n-1) {
                    p = 0
                }
            }
        } else {
            r++
        }

        var finished = false
        if (p >= n && q >= n && r >= n) {
            p = 0; q = 0; r = 0;
            finished = true
        }

        val extremePoints = PointSetEntity(points)
        if (finished) {
            listener.onFinish(extremePoints)
        } else {
            val triangle = PointSetEntity(listOf(points[p], points[q], points[r]))
            listener.onStep(triangle, extremePoints)
        }
    }

    override fun reset() {

        this.p = 0
        this.q = 0
        this.r = 0
    }

    private fun inTriangle(list: List<PointEntity>, pi: Int, qi: Int, ri: Int, si: Int): Boolean {

        val p = list[pi]
        val q = list[qi]
        val r = list[ri]
        val s = list[si]

        return  AlgorithmUtil.toLeft(p, q, s) &&
                AlgorithmUtil.toLeft(q, r, s) &&
                AlgorithmUtil.toLeft(r, p, s)

    }
}