package dev.eduayuso.cgkotlin.shared.domain.usecases

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity

interface IConvexHullUseCases: IUseCases {

    fun getAlgorithmList(): List<String>

    fun getAlgorithm(selection: String): IConvexHullAlgorithm

    fun createRandomPointSet(max: Int): PointSetEntity

    suspend fun runConvexHullAlgorithm(
            algorithm: IConvexHullAlgorithm,
            points: PointSetEntity,
            listener: IConvexHullTaskListener)

    fun stepConvexHullAlgorithm(
            algorithm: IConvexHullAlgorithm,
            points: PointSetEntity,
            listener: IConvexHullTaskListener)
}