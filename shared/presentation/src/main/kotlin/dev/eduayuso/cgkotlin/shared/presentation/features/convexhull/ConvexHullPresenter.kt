package dev.eduayuso.cgkotlin.shared.presentation.features.convexhull

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.usecases.IConvexHullUseCases
import dev.eduayuso.cgkotlin.shared.presentation.CGPresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConvexHullPresenter(

    override val interactor: IConvexHullUseCases

): CGPresenter<IConvexHullEvents>() {

    private var maxPoints = 10
    private var pointSet: PointSetEntity? = null
    private var selectedAlgorithm: IConvexHullAlgorithm? = null

    fun createRandomPointSet() {

        selectedAlgorithm?.reset()

        this.interactor.createRandomPointSet(this.maxPoints).let {

            this.pointSet = it
            this.listener?.onPointSetProvided(it)
        }
    }

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

    fun setMaxPoints(max: Int) {

        this.maxPoints = max
    }

    fun findExtremePoints(listener: IConvexHullTaskListener) {

        this.listener?.onStartRunningAlgorithm()

        if (this.selectedAlgorithm == null || this.pointSet == null) {
            this.listener?.onValidationFailed()

        } else {

            GlobalScope.launch {

                interactor.runConvexHullAlgorithm(selectedAlgorithm!!, pointSet!!, listener)
            }
        }
    }

    fun findExtremePointsByStep(listener: IConvexHullTaskListener) {

        interactor.stepConvexHullAlgorithm(selectedAlgorithm!!, pointSet!!, listener)
    }
}