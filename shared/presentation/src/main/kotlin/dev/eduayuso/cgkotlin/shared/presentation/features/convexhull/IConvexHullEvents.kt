package dev.eduayuso.cgkotlin.shared.presentation.features.convexhull

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.presentation.mvp.IViewEvents

interface IConvexHullEvents: IViewEvents {

    fun onAlgorithmListFetched(list: List<String>)

    fun onAlgorithmSelected(algorithm: IConvexHullAlgorithm)

    fun onPointSetProvided(points: PointSetEntity)

    fun onStartRunningAlgorithm()

    fun onValidationFailed()

    fun onExtremePointsFound(points: PointSetEntity)
}