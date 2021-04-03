package dev.eduayuso.cgkotlin.features.convexhull

import dev.eduayuso.cgkotlin.shared.di.SharedFactory
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.presentation.CGUtil
import dev.eduayuso.cgkotlin.shared.presentation.features.convexhull.IConvexHullEvents
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.paint.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tornadofx.*

class ConvexHullView:

    View("Computational Geometry with Kotlin"), IConvexHullEvents {

    val presenter by lazy {
        SharedFactory.createConvexHullPresenter().apply {
            listener = this@ConvexHullView
        }
    }

    val listener = object: IConvexHullTaskListener {

        private var startTime = 0L

        override fun onStart(input: PointSetEntity) {

            startTime = System.currentTimeMillis()
        }

        override fun onStep(helper: PointSetEntity?, output: PointSetEntity?) {

            GlobalScope.launch(Dispatchers.Main) {

                status.clear()
                status.writeLog("Running algorithm ...")
                val totalTime = System.currentTimeMillis() - startTime
                status.writeLog("Time elapsed: ${CGUtil.parseTime(totalTime)}")

                output?.let { onExtremePointsFound(it) }
                helper?.let { canvas.drawHelperLines(it, Color.BLUE) }
            }
        }

        override fun onFinish(output: PointSetEntity) {

            GlobalScope.launch(Dispatchers.Main) {

                status.writeLog("FINISHED")
                onExtremePointsFound(output)
            }
        }
    }

    val form: ConvexHullFormView by inject()
    val status: ConvexHullStatusView by inject()
    val canvas: ConvexHullCanvasView by inject()

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

    override fun onAlgorithmSelected(algorithm: IConvexHullAlgorithm) {

        if (algorithm.stepOption) this.form.stepButton.show()
        else this.form.stepButton.hide()
    }

    override fun onPointSetProvided(pointSet: PointSetEntity) {

        this.canvas.draw(pointSet)
    }

    override fun onStartRunningAlgorithm() {

        this.status.clear()
    }


    override fun onExtremePointsFound(points: PointSetEntity) {

        this.canvas.draw(points)
    }

    override fun onValidationFailed() {

        val alert = Alert(AlertType.WARNING)
        alert.title = ""
        alert.contentText = "You have to generate the point set and select algorithm"
        alert.showAndWait()
    }
}
