package dev.eduayuso.cgkotlin.features.segmentintersection

import dev.eduayuso.cgkotlin.components.CanvasView
import dev.eduayuso.cgkotlin.components.StatusView
import dev.eduayuso.cgkotlin.shared.di.SharedFactory
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import dev.eduayuso.cgkotlin.shared.presentation.CGUtil
import dev.eduayuso.cgkotlin.shared.presentation.features.segmentintersection.ISegmentIntersectionEvents
import dev.eduayuso.cgkotlin.shared.presentation.features.segmentintersection.SegmentIntersectionPresenter
import javafx.scene.Parent
import javafx.scene.paint.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tornadofx.*

class SegmentIntersectionView:

    Fragment("Segment Intersection"),
    ISegmentIntersectionEvents {

    val presenter by lazy {
        SharedFactory.createSegmentIntersectionPresenter().apply {
            listener = this@SegmentIntersectionView
        }
    }

    val form: SegmentIntersectionFormView by inject()
    val status: SegmentIntersectionStatusView by inject()
    val canvas: SegmentIntersectionCanvasView by inject()

    val listener = object: ISegmentIntersectionTaskListener {

        private var startTime = 0L

        override fun onStart(input: SegmentSetEntity) {

            startTime = System.currentTimeMillis()
        }

        override fun onStep(helper: SegmentSetEntity?, output: PointSetEntity?, extra: Float?) {

            GlobalScope.launch(Dispatchers.Main) {

                status.clear()
                status.writeLog("Running algorithm ...")
                val totalTime = System.currentTimeMillis() - startTime
                status.writeLog("Time elapsed: ${CGUtil.parseTime(totalTime)}")

                //output?.let { onIntersectionsFound(it, output!!) }
                extra?.let { canvas.drawSweepLine(it) }
            }
        }

        override fun onFinish(output: PointSetEntity) {

            GlobalScope.launch(Dispatchers.Main) {

                val intersectionsNum = output.list.size/2
                status.writeLog("FINISHED: $intersectionsNum intersections found!")
                val segments = presenter.getSegmentSet()
                onIntersectionsFound(segments, output)
            }
        }
    }

    init {

        form.presenter = presenter
        form.listener = listener
        presenter.getAlgorithmList()
    }

    override val root = hbox {
        vbox {
            add(form)
            add(status)
        }
        add(canvas)
    }

    override fun onAlgorithmListFetched(list: List<String>) {

        this.form.populateAlgorithmList(list)
    }

    override fun onAlgorithmSelected(algorithm: ISegmentIntersectionAlgorithm) {

        if (algorithm.stepOption) this.form.stepButton.show()
        else this.form.stepButton.hide()
    }

    override fun onSegmentSetProvided(segments: SegmentSetEntity) {

        this.canvas.draw(segments)
    }

    override fun onStartRunningAlgorithm() {

        this.status.clear()
    }

    override fun onIntersectionsFound(segments: SegmentSetEntity, points: PointSetEntity) {

        this.canvas.draw(segments)
        this.canvas.drawIntersections(points)
    }
}