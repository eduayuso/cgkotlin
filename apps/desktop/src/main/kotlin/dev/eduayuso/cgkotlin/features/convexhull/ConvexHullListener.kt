package dev.eduayuso.cgkotlin.features.convexhull

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity

class ConvexHullListener: IConvexHullTaskListener {

    override fun onStart(input: PointSetEntity) {
    }

    override fun onStep(helper:PointSetEntity?, output: PointSetEntity?, extra: Float?) {
    }

    override fun onFinish(output: PointSetEntity) {
    }
}