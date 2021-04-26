package dev.eduayuso.cgkotlin.features.segmentintersection

import dev.eduayuso.cgkotlin.components.CanvasView
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import javafx.scene.paint.Color

class SegmentIntersectionCanvasView: CanvasView<SegmentSetEntity>() {

    override fun draw(segmentSet: SegmentSetEntity) {

        if (segmentSet.list.size > 100) radius = 2.0

        pointsGroup.children.clear()

        pointsGroup.apply {

            segmentSet.list.forEach { segment ->

                drawSegment(segment, Color.GREEN, Color.BLUE)
            }
        }
    }

    fun drawIntersections(points: PointSetEntity) {

        points.list.forEach {

            this.drawPoint(it, 2.0, Color.RED)
        }
    }
}