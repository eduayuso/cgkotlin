package dev.eduayuso.cgkotlin.features.convexhull

import dev.eduayuso.cgkotlin.components.Styles
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import javafx.scene.Group
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import tornadofx.*

class ConvexHullCanvasView: View() {

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

    fun draw(pointSet: PointSetEntity) {

        if (pointSet.list.size > 100) radius = 2.0

        pointsGroup.children.clear()

        pointsGroup.apply {

            pointSet.list.forEach { point ->

                if (point.isExtreme) {
                    drawPoint(this, point, radius+1.0, Color.GREEN)
                } else {
                    drawPoint(this, point, radius, Color.RED)
                }
            }

            val list = pointSet.list.filter { it.isExtreme }
            if (list.isNotEmpty()) {
                drawExtremeEdges(list, Color.GREEN)
            }
        }
    }

    private fun drawPoint(group: Group, point: PointEntity, r: Double, color: Color) {

        group.circle {
            centerX = point.x * maxWidth
            centerY = point.y * maxHeight
            radius = r
            fill = color
        }
    }

    private fun drawExtremeEdges(points: List<PointEntity>, color: Color) {

        points.forEach {
            p1 ->
            p1.successor?.let {
                p2 -> drawLine(p1, p2, color)
            }
        }
    }

    private fun drawLine(p1: PointEntity, p2: PointEntity, color: Color) {

        this.pointsGroup.line {
            startX = p1.x * maxWidth
            startY = p1.y * maxHeight
            endX   = p2.x * maxWidth
            endY   = p2.y * maxHeight
            stroke = color
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