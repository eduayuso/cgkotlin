package dev.eduayuso.cgkotlin.shared.impl.interactors

import dev.eduayuso.cgkotlin.shared.data.IDataManager
import dev.eduayuso.cgkotlin.shared.data.IInteractor
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import dev.eduayuso.cgkotlin.shared.domain.usecases.IConvexHullUseCases
import dev.eduayuso.cgkotlin.shared.domain.usecases.ISegmentIntersectionUseCases
import dev.eduayuso.cgkotlin.shared.impl.algorithms.segmentintersection.BruteForceAlgorithm
import dev.eduayuso.cgkotlin.shared.impl.algorithms.segmentintersection.SweepLineAlgorithm

class SegmentIntersectionInteractor(

    override val data: IDataManager

): ISegmentIntersectionUseCases, IInteractor {

    override fun getAlgorithmList() = this.data.segmentIntersection.algorithms.keys.toList()

    override fun getAlgorithm(selection: String): ISegmentIntersectionAlgorithm =

        this.data.segmentIntersection.algorithms[selection]!!

    override fun createRandomSegmentSet(max: Int): SegmentSetEntity {

        var list = emptyList<SegmentEntity>().toMutableList()

        for (i in 1..max) {
            SegmentEntity(
                a = PointEntity(randomValue(), randomValue()),
                b = PointEntity(randomValue(), randomValue())
            ).let { list.add(it) }
        }

        return SegmentSetEntity(list)
    }

    private fun randomValue() = Math.random().toFloat()

    override suspend fun runSegmentIntersectionAlgorithm(
        algorithm: ISegmentIntersectionAlgorithm,
        segments: SegmentSetEntity,
        listener: ISegmentIntersectionTaskListener) {

        algorithm.run(segments, listener)
    }

    override fun stepSegmentIntersectionAlgorithm(
        algorithm: ISegmentIntersectionAlgorithm,
        segments: SegmentSetEntity,
        listener: ISegmentIntersectionTaskListener) {

        algorithm.step(segments, listener)
    }
}
