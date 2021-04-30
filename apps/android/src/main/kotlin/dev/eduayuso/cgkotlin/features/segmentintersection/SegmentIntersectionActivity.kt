package dev.eduayuso.cgkotlin.features.segmentintersection

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import dev.eduayuso.cgkotlin.R
import dev.eduayuso.cgkotlin.components.ui.CGActivity
import dev.eduayuso.cgkotlin.components.ui.CGCanvas
import dev.eduayuso.cgkotlin.shared.di.SharedFactory
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import dev.eduayuso.cgkotlin.shared.presentation.CGUtil
import dev.eduayuso.cgkotlin.shared.presentation.features.segmentintersection.ISegmentIntersectionEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class SegmentIntersectionActivity:
    CGActivity<ISegmentIntersectionEvents>(),
    ISegmentIntersectionEvents {

    override val presenter by lazy {
        SharedFactory.createSegmentIntersectionPresenter().apply {
            listener = this@SegmentIntersectionActivity
        }
    }
    override val layoutResourceId = R.layout.activity_segment_intersection

    val spinner by lazy { findViewById<Spinner>(R.id.algorithmSpinner) }
    val canvas by lazy { findViewById<CGCanvas>(R.id.segmentIntersectionCanvas) }
    val pointsNumEditText by lazy { findViewById<EditText>(R.id.pointsNumEditText) }
    val generateButton by lazy { findViewById<Button>(R.id.generateButton) }
    val runButton by lazy { findViewById<Button>(R.id.runButton) }
    val stepButton by lazy { findViewById<Button>(R.id.stepButton) }

    val listener = object: ISegmentIntersectionTaskListener {

        private var startTime = 0L

        override fun onStart(input: SegmentSetEntity) {

            startTime = System.currentTimeMillis()
        }

        override fun onStep(helper: SegmentSetEntity?, output: PointSetEntity?) {

            GlobalScope.launch(Dispatchers.Main) {

                Log.d("RUNNING","Running algorithm ...")
                val totalTime = System.currentTimeMillis() - startTime
                Log.d("RUNNING","Time elapsed: ${CGUtil.parseTime(totalTime)}")

                output?.let {
                    onIntersectionsFound(presenter.getSegmentSet(), it)
                    canvas.helperPoints = it
                    canvas.invalidate()
                }
            }
        }

        override fun onFinish(output: PointSetEntity) {

            GlobalScope.launch(Dispatchers.Main) {

                //status.writeLog("FINISHED")
                onIntersectionsFound(presenter.getSegmentSet(), output)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        this.generateButton.setOnClickListener {
            var maxSegments = 10;
            try {
                maxSegments = Integer.parseInt(this.pointsNumEditText.text.toString())
            } catch (e: Exception) {}
            this.presenter.setMaxSegments(maxSegments)
            this.presenter.createRandomSegmentSet()
        }
        this.runButton.setOnClickListener {
            this.presenter.findIntersections(this.listener)
        }
        this.stepButton.setOnClickListener {
            this.presenter.findIntersectionsByStep(this.listener)
        }
        this.presenter.createRandomSegmentSet()
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

    override fun onAlgorithmSelected(algorithm: ISegmentIntersectionAlgorithm) {

        if (algorithm.stepOption) this.stepButton.visibility = View.VISIBLE
        else this.stepButton.visibility = View.GONE
    }

    override fun onSegmentSetProvided(segments: SegmentSetEntity) {

        canvas.segments = segments
        canvas.points = null
        canvas.invalidate()
    }

    override fun onStartRunningAlgorithm() {

        canvas.helperPoints = null
        canvas.invalidate()
    }

    override fun onIntersectionsFound(segments: SegmentSetEntity, points: PointSetEntity) {

        canvas.segments = segments
        canvas.points = points
        canvas.invalidate()
    }
}