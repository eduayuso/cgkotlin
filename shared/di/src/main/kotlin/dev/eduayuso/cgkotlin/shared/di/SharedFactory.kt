package dev.eduayuso.cgkotlin.shared.di

import dev.eduayuso.cgkotlin.shared.impl.DataManager
import dev.eduayuso.cgkotlin.shared.impl.interactors.ConvexHullInteractor
import dev.eduayuso.cgkotlin.shared.impl.interactors.SegmentIntersectionInteractor
import dev.eduayuso.cgkotlin.shared.impl.services.ConvexHullService
import dev.eduayuso.cgkotlin.shared.impl.services.SegmentIntersectionService
import dev.eduayuso.cgkotlin.shared.presentation.features.convexhull.ConvexHullPresenter
import dev.eduayuso.cgkotlin.shared.presentation.features.segmentintersection.SegmentIntersectionPresenter

object SharedFactory {

    private val dataManager by lazy {
        DataManager(
            convexHull = ConvexHullService(),
            segmentIntersection = SegmentIntersectionService()
        )
    }

    fun createConvexHullPresenter() =

        ConvexHullPresenter(
            interactor = ConvexHullInteractor(dataManager)
        )

    fun createSegmentIntersectionPresenter() =

        SegmentIntersectionPresenter(
            interactor = SegmentIntersectionInteractor(dataManager)
        )
}