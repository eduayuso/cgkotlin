package dev.eduayuso.cgkotlin.features.convexhull

import dev.eduayuso.cgkotlin.components.CanvasView
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import javafx.scene.paint.Color

class ConvexHullCanvasView: CanvasView<PointSetEntity>() {

    override fun draw(pointSet: PointSetEntity) {

        if (pointSet.list.size > 100) radius = 2.0

        pointsGroup.children.clear()

        pointsGroup.apply {

            pointSet.list.forEach { point ->

                if (point.isExtreme) {
                    drawPoint(point, radius+1.0, Color.GREEN)
                } else {
                    drawPoint(point, radius, Color.RED)
                }
            }

            val list = pointSet.list.filter { it.isExtreme }
            if (list.isNotEmpty()) {
                drawExtremeEdges(list, Color.GREEN)
            }
        }
    }
}