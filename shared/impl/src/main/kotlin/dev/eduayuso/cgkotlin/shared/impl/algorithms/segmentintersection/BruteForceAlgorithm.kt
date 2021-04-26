package dev.eduayuso.cgkotlin.shared.impl.algorithms.segmentintersection

import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import dev.eduayuso.cgkotlin.shared.impl.algorithms.AlgorithmUtil

/**
 * Seep Line algorithm, also known as Bentley and Ottman algorithm
 */
object BruteForceAlgorithm: ISegmentIntersectionAlgorithm {

    override val stepOption = false

    override fun run(input: SegmentSetEntity, listener: ISegmentIntersectionTaskListener) {

        val output = emptyList<PointEntity>().toMutableList()

        listener.onStart(input)

        input.list.forEach { p->
            input.list.forEach { q->
                if (p != q) {
                    AlgorithmUtil.findIntersection(p, q)?.let {
                        intersectionPoint ->
                        output.add(intersectionPoint)
                    }
                }
            }
            listener.onStep(null, null)
        }

        listener.onFinish(PointSetEntity(output))
    }

    override fun step(input: SegmentSetEntity, listener: ISegmentIntersectionTaskListener) {
    }

    override fun reset() {
    }
}