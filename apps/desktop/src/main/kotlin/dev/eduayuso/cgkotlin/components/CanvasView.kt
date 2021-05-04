package dev.eduayuso.cgkotlin.components

import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import javafx.scene.Group
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import tornadofx.*

abstract class CanvasView<T>: View() {

    lateinit var pointsGroup: Group

    override val root = stackpane {

        addClass(Styles.paneBackground)
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS
        pointsGroup = group {  }
    }

    var radius = 3.0
    val maxWidth get() = root.width * 0.8
    val maxHeight get() = root.height * 0.8

    abstract fun draw(set: T)

    fun clear() {

        pointsGroup.children.clear()
    }

    protected fun drawPoint(point: PointEntity, r: Double, color: Color) {

        pointsGroup.circle {
            centerX = point.x * maxWidth
            centerY = point.y * maxHeight
            radius = r
            fill = color
        }
    }

    protected fun drawExtremeEdges(points: List<PointEntity>, color: Color) {

        points.forEach {
            p1 ->
            p1.successor?.let {
                p2 -> drawLine(p1, p2, color)
            }
        }
    }

    protected fun drawLine(p1: PointEntity, p2: PointEntity, color: Color): Line =

        this.pointsGroup.line {
            startX = p1.x * maxWidth
            startY = p1.y * maxHeight
            endX   = p2.x * maxWidth
            endY   = p2.y * maxHeight
            stroke = color
        }

    /**
     * Draw the two points that defines the segment, and the line between them
     */
    fun drawSegment(segment: SegmentEntity, lineColor: Color, pointsColor: Color) {

        this.drawLine(segment.a, segment.b, lineColor)
        this.drawPoint(segment.a, 2.0, pointsColor)
        this.drawPoint(segment.b, 2.0, pointsColor)
    }

    fun dragSegments(segments: SegmentSetEntity, color: Color) {

        segments.list.forEach {
            drawSegment(it, color, Color.BLUE)
        }
    }

    fun drawHelperLines(it: PointSetEntity, color: Color) {

        // Draw triangle (TriangleTestAlgorithm)
        if (it.list.size == 3) {
            this.drawLine(it.list[0], it.list[1], color)
            this.drawLine(it.list[1], it.list[2], color)
            this.drawLine(it.list[2], it.list[0], color)

        // Else: single line
        } else {
            this.drawLine(it.list[0], it.list[1], color)
        }
    }
}