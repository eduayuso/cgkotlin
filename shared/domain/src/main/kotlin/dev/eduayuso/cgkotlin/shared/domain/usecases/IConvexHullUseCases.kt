package dev.eduayuso.cgkotlin.shared.domain.usecases

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity

interface IConvexHullUseCases: IUseCases {

    fun getAlgorithmList(): List<String>

    fun providePointSet(): PointSetEntity

    fun createRandomPointSet(max: Int): PointSetEntity

    fun resetPointSet()

    fun editPointSet()

    fun getAlgorithm(selection: String): IConvexHullAlgorithm

    suspend fun runConvexHullAlgorithm(
        algorithm: IConvexHullAlgorithm,
        points: PointSetEntity,
        listener: IConvexHullTaskListener)

    fun stepConvexHullAlgorithm(
        algorithm: IConvexHullAlgorithm,
        points: PointSetEntity,
        listener: IConvexHullTaskListener)
}