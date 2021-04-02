package dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.impl.algorithms.AlgorithmUtil

object ExtremeEdgesToLeftAlgorithm: IConvexHullAlgorithm {

    override val stepOption = false

    override fun run(input: PointSetEntity, listener: IConvexHullTaskListener) {

        val points = input.list
        val n = input.list.size-1

        listener.onStart(input)

        for (p in 0..n) {

            for (q in p+1..n) {

                checkEdge(points, n, p, q)
            }

            listener.onStep(null, null)
        }

        val extremePoints = PointSetEntity(points)
        listener.onFinish(extremePoints)
    }

    override fun step(input: PointSetEntity, listener: IConvexHullTaskListener) {
        TODO("Not yet implemented")
    }

    override fun reset() {
    }

    private fun checkEdge(points: List<PointEntity>, n: Int, p: Int, q: Int) {

        var lEmpty = true
        var rEmpty = true

        val pp = points[p]
        val qq = points[q]

        for (k in 0..n) {

            if (!lEmpty && !rEmpty) break

            val kk = points[k]

            if (k != p && k != q) {

                if (AlgorithmUtil.toLeft(pp, qq, kk)) lEmpty = false
                else rEmpty = false
            }
        }

        if (lEmpty || rEmpty) {
            pp.isExtreme = true
            qq.isExtreme = true
        }
    }
}