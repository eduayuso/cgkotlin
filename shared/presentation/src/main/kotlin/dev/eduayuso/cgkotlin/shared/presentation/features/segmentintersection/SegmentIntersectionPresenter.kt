package dev.eduayuso.cgkotlin.shared.presentation.features.segmentintersection

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import dev.eduayuso.cgkotlin.shared.domain.usecases.ISegmentIntersectionUseCases
import dev.eduayuso.cgkotlin.shared.presentation.CGPresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SegmentIntersectionPresenter(

    override val interactor: ISegmentIntersectionUseCases

): CGPresenter<ISegmentIntersectionEvents>() {

    private var maxSegments = 10
    private var segmentSet: SegmentSetEntity? = null
    private var selectedAlgorithm: ISegmentIntersectionAlgorithm? = null

    fun createRandomSegmentSet() {

        this.interactor.createRandomSegmentSet(this.maxSegments).let {

            this.segmentSet = it
            this.listener?.onSegmentSetProvided(it)
        }
    }

    fun getSegmentSet() = this.segmentSet ?: SegmentSetEntity(emptyList())

    fun getAlgorithmList() {

        this.interactor.getAlgorithmList().let {

            this.selectAlgorithm(it.first())
            this.listener?.onAlgorithmListFetched(it)
        }
    }

    fun selectAlgorithm(selection: String) {

        this.selectedAlgorithm = this.interactor.getAlgorithm(selection)
        this.selectedAlgorithm?.let {
            this.listener?.onAlgorithmSelected(it)
        }
    }

    fun setMaxSegments(max: Int) {

        this.maxSegments = max
    }

    fun findIntersections(listener: ISegmentIntersectionTaskListener) {

        this.listener?.onStartRunningAlgorithm()

        GlobalScope.launch {

            interactor.runSegmentIntersectionAlgorithm(selectedAlgorithm!!, segmentSet!!, listener)
        }
    }

    fun findIntersectionssByStep(listener: ISegmentIntersectionTaskListener) {

        interactor.stepSegmentIntersectionAlgorithm(selectedAlgorithm!!, segmentSet!!, listener)
    }
}