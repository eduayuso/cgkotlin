package dev.eduayuso.cgkotlin.components.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity

class CGCanvas
    @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0

) : View(context, attrs, defStyleAttr) {

    var points: PointSetEntity? = null
    var segments: SegmentSetEntity? = null
    var helperPoints: PointSetEntity? = null
    private var pointRadius = 8f

    private fun pointStyle(isExtreme: Boolean) =
        Paint().apply { color = if (isExtreme) Color.GREEN else Color.RED}

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        this.drawPointSet(canvas)
        this.drawExtremeEdges(canvas)
        this.drawHelperLines(canvas)
        this.drawSegments(canvas)
    }

    private fun drawPointSet(canvas: Canvas?) {

        points?.list?.forEach {
            canvas?.drawCircle(
                normalizeValue(it.x, this.width),
                normalizeValue(it.y, this.height),
                pointRadius,
                pointStyle(it.isExtreme))
        }
    }

    private fun drawExtremeEdges(canvas: Canvas?) {

        val segmentStyle = Paint().apply {
            Color.GREEN
            strokeWidth = 4.0F
        }

        points?.list?.filter { it.isExtreme }?.let { list ->

            if (list.isNotEmpty()) {
                list.forEach { p1 ->
                    p1.successor?.let { p2 ->
                        drawLine(canvas, p1, p2, segmentStyle)
                    }
                }
            }
        }
    }

    private fun drawLine(canvas: Canvas?, p1: PointEntity, p2: PointEntity, segmentStyle: Paint) {

        canvas?.drawLine(
            normalizeValue(p1.x, this.width),
            normalizeValue(p1.y, this.height),
            normalizeValue(p2.x, this.width),
            normalizeValue(p2.y, this.height),
            segmentStyle
        )
    }

    private fun drawSegments(canvas: Canvas?) {

        val segmentStyle = Paint().apply {
            Color.GREEN
            strokeWidth = 3.0F
        }

        segments?.list?.let { list ->

            list.forEach { segment ->
                drawLine(canvas, segment.a, segment.b, segmentStyle)
            }
        }
    }

    private fun normalizeValue(value: Float, max: Int) =

        pointRadius + (value * (max - pointRadius*2))

    private fun drawHelperLines(canvas: Canvas?) {

        val segmentStyle = Paint().apply {
            Color.BLUE
            strokeWidth = 30.0F
        }

        this.helperPoints?.let {
            if (it.list.size == 3) {
                this.drawLine(canvas, it.list[0], it.list[1], segmentStyle)
                this.drawLine(canvas, it.list[1], it.list[2], segmentStyle)
                this.drawLine(canvas, it.list[2], it.list[0], segmentStyle)

                // Else: single line
            } else {
                this.drawLine(canvas, it.list[0], it.list[1], segmentStyle)
            }
        }
    }
}