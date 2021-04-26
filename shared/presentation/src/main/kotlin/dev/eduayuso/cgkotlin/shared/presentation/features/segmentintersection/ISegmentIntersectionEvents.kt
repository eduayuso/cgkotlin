package dev.eduayuso.cgkotlin.shared.presentation.features.segmentintersection

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import dev.eduayuso.cgkotlin.shared.presentation.mvp.IViewEvents

interface ISegmentIntersectionEvents: IViewEvents {

    fun onAlgorithmListFetched(list: List<String>)

    fun onAlgorithmSelected(algorithm: ISegmentIntersectionAlgorithm)

    fun onSegmentSetProvided(segments: SegmentSetEntity)

    fun onStartRunningAlgorithm()

    fun onIntersectionsFound(segments: SegmentSetEntity, points: PointSetEntity)
}