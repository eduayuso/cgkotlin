package dev.eduayuso.cgkotlin.shared.domain.usecases

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity

interface ISegmentIntersectionUseCases: IUseCases {

    fun getAlgorithmList(): List<String>

    fun getAlgorithm(selection: String): ISegmentIntersectionAlgorithm

    fun createRandomSegmentSet(max: Int): SegmentSetEntity

    suspend fun runSegmentIntersectionAlgorithm(
            algorithm: ISegmentIntersectionAlgorithm,
            segments: SegmentSetEntity,
            listener: ISegmentIntersectionTaskListener)

    fun stepSegmentIntersectionAlgorithm(
            algorithm: ISegmentIntersectionAlgorithm,
            segments: SegmentSetEntity,
            listener: ISegmentIntersectionTaskListener)
}