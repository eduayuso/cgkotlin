package dev.eduayuso.cgkotlin.shared.di

import dev.eduayuso.cgkotlin.shared.impl.DataManager
import dev.eduayuso.cgkotlin.shared.impl.interactors.ConvexHullInteractor
import dev.eduayuso.cgkotlin.shared.impl.services.ConvexHullService
import dev.eduayuso.cgkotlin.shared.presentation.features.convexhull.ConvexHullPresenter

object SharedFactory {

    private val dataManager by lazy {
        DataManager(
            convexHull = ConvexHullService()
        )
    }

    fun createConvexHullPresenter() = ConvexHullPresenter(
            interactor = ConvexHullInteractor(dataManager)
        )
}