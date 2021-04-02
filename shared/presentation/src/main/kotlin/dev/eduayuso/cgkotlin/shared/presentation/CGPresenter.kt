package dev.eduayuso.cgkotlin.shared.presentation

import dev.eduayuso.cgkotlin.shared.domain.usecases.IUseCases

/**
 * Base View Presenter for this project
 */
abstract class CGPresenter<ViewListenerClass> {

    abstract val interactor: IUseCases

    var listener: ViewListenerClass? = null
}