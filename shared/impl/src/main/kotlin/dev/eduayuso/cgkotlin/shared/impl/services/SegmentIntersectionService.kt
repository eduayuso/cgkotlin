package dev.eduayuso.cgkotlin.shared.impl.services

import dev.eduayuso.cgkotlin.shared.data.services.ISegmentIntersectionService
import dev.eduayuso.cgkotlin.shared.impl.DataConstants
import dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull.ExtremeEdgesToLeftAlgorithm
import dev.eduayuso.cgkotlin.shared.impl.algorithms.segmentintersection.BruteForceAlgorithm
import dev.eduayuso.cgkotlin.shared.impl.algorithms.segmentintersection.SweepLineAlgorithm

class SegmentIntersectionService: ISegmentIntersectionService() {

    val names = DataConstants.SegmentIntersectionAlgorithms

    override val algorithms = mapOf(

        names.bruteForce to BruteForceAlgorithm,
        names.sweepLine  to SweepLineAlgorithm
    )
}