package dev.eduayuso.cgkotlin.components.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity

class CGCanvas
    @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0

) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        this.drawPointSet(canvas)
    }

    var points: PointSetEntity? = null

    private var pointRadius = 8f

    private fun pointStyle(isExtreme: Boolean) =
            Paint().apply { color = if (isExtreme) Color.GREEN else Color.RED}

    private fun drawPointSet(canvas: Canvas?) {

        points?.list?.forEach {
            canvas?.drawCircle(
                normalizeValue(it.x, this.width),
                normalizeValue(it.y, this.height),
                pointRadius,
                pointStyle(it.isExtreme))
        }

        points?.list?.filter { it.isExtreme }?.let {
            list ->
                if (list.isNotEmpty()) {
                    drawExtremeEdges(canvas, list, Color.GREEN)
                }
        }
    }

    private fun drawExtremeEdges(canvas: Canvas?, points: List<PointEntity>, color: Int) {

        val segmentStyle = Paint().apply { color }

        points.forEach {

            p1 ->
            p1.successor?.let { p2 ->
                canvas?.drawLine(
                    normalizeValue(p1.x, this.width),
                    normalizeValue(p1.y, this.height),
                    normalizeValue(p2.x, this.width),
                    normalizeValue(p2.y, this.height),
                    segmentStyle
                )
            }
        }
    }

    private fun normalizeValue(value: Float, max: Int) =

        pointRadius + (value * (max - pointRadius*2))
}