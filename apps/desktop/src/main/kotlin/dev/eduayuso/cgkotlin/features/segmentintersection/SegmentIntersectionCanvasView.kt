package dev.eduayuso.cgkotlin.features.segmentintersection

import dev.eduayuso.cgkotlin.components.CanvasView
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import tornadofx.removeFromParent

class SegmentIntersectionCanvasView: CanvasView<SegmentSetEntity>() {

    private var sweepLine: Line? = null

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

    fun drawSweepLine(x: Float) {

        sweepLine?.removeFromParent()
        val p1 = PointEntity(x, 0f)
        val p2 = PointEntity(x, 1f)
        sweepLine = this.drawLine(p1, p2, Color.RED)
    }
}