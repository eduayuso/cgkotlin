package dev.eduayuso.cgkotlin.shared.impl.interactors

import dev.eduayuso.cgkotlin.shared.data.IDataManager
import dev.eduayuso.cgkotlin.shared.data.IInteractor
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.usecases.IConvexHullUseCases

class ConvexHullInteractor(

    override val data: IDataManager

): IConvexHullUseCases, IInteractor {

    override fun getAlgorithmList() = this.data.convexHull.algorithms.keys.toList()

    override fun getAlgorithm(selection: String): IConvexHullAlgorithm =

        this.data.convexHull.algorithms[selection]!!

    override fun createRandomPointSet(max: Int): PointSetEntity {

        var list = emptyList<PointEntity>().toMutableList()

        for (i in 1..max) list.add(PointEntity(randomValue(), randomValue()))

        return PointSetEntity(list)
    }

    private fun randomValue() = Math.random().toFloat()

    override suspend fun runConvexHullAlgorithm(
            algorithm: IConvexHullAlgorithm,
            points: PointSetEntity,
            listener: IConvexHullTaskListener) {

        algorithm.run(points, listener)
    }

    override fun stepConvexHullAlgorithm(
            algorithm: IConvexHullAlgorithm,
            points: PointSetEntity,
            listener: IConvexHullTaskListener) {

        algorithm.step(points, listener)
    }
}