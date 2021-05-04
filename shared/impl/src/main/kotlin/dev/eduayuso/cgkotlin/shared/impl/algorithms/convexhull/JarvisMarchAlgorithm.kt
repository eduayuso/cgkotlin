package dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.impl.algorithms.AlgorithmUtil

object JarvisMarchAlgorithm: IConvexHullAlgorithm {

    override val stepOption = true

    private var ltl = -1
    private var k = -1
    private var s = -1
    private var t = -1

    override fun run(input: PointSetEntity, listener: IConvexHullTaskListener) {

        val points = input.list
        val n = input.list.size

        listener.onStart(input)

        val ltl = AlgorithmUtil.lowerThenLeftMost(input)
        var k = ltl

        do {

            points[k].apply { isExtreme = true }
            var s = -1

            for (t in 0 until n) {
                if (t != k && (s==-1 || !AlgorithmUtil.toLeft(input, k, s, t))) {
                    s = t
                }
            }
            points[k].successor = points[s]
            k = s

            listener.onStep(null, null, null)

        } while (k != ltl)

        listener.onFinish(input)
    }

    override fun step(input: PointSetEntity, listener: IConvexHullTaskListener) {

        val points = input.list
        val n = input.list.size

        // Initial step
        if (ltl == -1) {

            listener.onStart(input)
            ltl = AlgorithmUtil.lowerThenLeftMost(input)
            k = ltl
            t = 0
        }

        // General case

        if (t == 0) {
            points[k].apply { isExtreme = true }
            s = -1
        }

        var endLoop = false
        for (tt in t until n) {

            if (tt != k && s > -1) {
                val helper = PointSetEntity(listOf(points[k], points[s]))
                val extreme = PointSetEntity(input.list)
                listener.onStep(helper, extreme, null)
            }

            if (tt != k && (s == -1 || !AlgorithmUtil.toLeft(input, k, s, tt))) {
                s = tt
                t = tt+1
                endLoop = tt == n-1
                break
            }
            endLoop = tt == n-1
        }

        if (endLoop) {

            points[k].successor = points[s]
            k = s
            t = 0

            // Check if is final step
            if (k == ltl) {
                val extreme = PointSetEntity(input.list)
                listener.onFinish(extreme)
            }
        }
    }

    override fun reset() {

        this.ltl = -1
        this.k = -1
        this.s = -1
        this.t = -1
    }
}