package dev.eduayuso.cgkotlin.features.convexhull

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import dev.eduayuso.cgkotlin.R
import dev.eduayuso.cgkotlin.components.ui.CGActivity
import dev.eduayuso.cgkotlin.components.ui.CGCanvas
import dev.eduayuso.cgkotlin.shared.di.SharedFactory
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.presentation.CGUtil
import dev.eduayuso.cgkotlin.shared.presentation.features.convexhull.IConvexHullEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class ConvexHullActivity:
    CGActivity<IConvexHullEvents>(),
    IConvexHullEvents {

    override val presenter by lazy {
        SharedFactory.createConvexHullPresenter().apply {
            listener = this@ConvexHullActivity
        }
    }
    override val layoutResourceId = R.layout.activity_convex_hull

    val spinner by lazy { findViewById<Spinner>(R.id.algorithmSpinner) }
    val canvas by lazy { findViewById<CGCanvas>(R.id.convexHullCanvas) }
    val pointsNumEditText by lazy { findViewById<EditText>(R.id.pointsNumEditText) }
    val generateButton by lazy { findViewById<Button>(R.id.generateButton) }
    val runButton by lazy { findViewById<Button>(R.id.runButton) }
    val stepButton by lazy { findViewById<Button>(R.id.stepButton) }

    val listener = object: IConvexHullTaskListener {

        private var startTime = 0L

        override fun onStart(input: PointSetEntity) {

            startTime = System.currentTimeMillis()
        }

        override fun onStep(helper: PointSetEntity?, output: PointSetEntity?) {

            GlobalScope.launch(Dispatchers.Main) {

                Log.d("RUNNING","Running algorithm ...")
                val totalTime = System.currentTimeMillis() - startTime
                Log.d("RUNNING","Time elapsed: ${CGUtil.parseTime(totalTime)}")

                output?.let { onExtremePointsFound(it) }
                helper?.let {
                    canvas.helperPoints = it
                    canvas.invalidate()
                }
            }
        }

        override fun onFinish(output: PointSetEntity) {

            GlobalScope.launch(Dispatchers.Main) {

                //status.writeLog("FINISHED")
                onExtremePointsFound(output)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        this.generateButton.setOnClickListener {
            var maxPoints = 10;
            try {
                maxPoints = Integer.parseInt(this.pointsNumEditText.text.toString())
            } catch (e: Exception) {}
            this.presenter.setMaxPoints(maxPoints)
            this.presenter.createRandomPointSet()
        }
        this.runButton.setOnClickListener {
            this.presenter.findExtremePoints(this.listener)
        }
        this.stepButton.setOnClickListener {
            this.presenter.findExtremePointsByStep(this.listener)
        }
        this.presenter.createRandomPointSet()
        this.presenter.getAlgorithmList()
    }

    override fun onAlgorithmListFetched(list: List<String>) {

        this.spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)

        this.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                val selection = parent.getItemAtPosition(position) as String
                presenter.selectAlgorithm(selection)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onAlgorithmSelected(algorithm: IConvexHullAlgorithm) {

        if (algorithm.stepOption) this.stepButton.visibility = View.VISIBLE
        else this.stepButton.visibility = View.GONE
    }

    override fun onPointSetProvided(pointSet: PointSetEntity) {

        canvas.points = pointSet
        canvas.invalidate()
    }

    override fun onStartRunningAlgorithm() {

        canvas.helperPoints = null
        canvas.invalidate()
    }

    override fun onValidationFailed() {
        TODO("Not yet implemented")
    }

    override fun onExtremePointsFound(points: PointSetEntity) {

        canvas.points = points
        canvas.invalidate()
    }
}