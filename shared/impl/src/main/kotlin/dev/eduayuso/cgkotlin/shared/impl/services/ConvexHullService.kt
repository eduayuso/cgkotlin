package dev.eduayuso.cgkotlin.shared.impl.services

import dev.eduayuso.cgkotlin.shared.data.services.IConvexHullService
import dev.eduayuso.cgkotlin.shared.impl.DataConstants
import dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull.ExtremeEdgesToLeftAlgorithm
import dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull.InTriangleAlgorithm
import dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull.JarvisMarchAlgorithm

class ConvexHullService: IConvexHullService() {

    val names = DataConstants.ConvexHullAlgorithms

    override val algorithms = mapOf(

        names.inTriangleTest        to InTriangleAlgorithm,
        names.extremeEdgeToLeftTest to ExtremeEdgesToLeftAlgorithm,
        names.jarvisMarch           to JarvisMarchAlgorithm
    )
}