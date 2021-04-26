package dev.eduayuso.cgkotlin.shared.impl.algorithms.segmentintersection

import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity

/**
 * Seep Line algorithm, also known as Bentley and Ottman algorithm
 */
object SweepLineAlgorithm: ISegmentIntersectionAlgorithm {

    override val stepOption = true

    override fun run(input: SegmentSetEntity, listener: ISegmentIntersectionTaskListener) {

    }

    override fun step(input: SegmentSetEntity, listener: ISegmentIntersectionTaskListener) {
    }

    override fun reset() {
    }
}