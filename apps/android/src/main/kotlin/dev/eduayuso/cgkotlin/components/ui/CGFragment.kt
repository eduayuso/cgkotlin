package dev.eduayuso.cgkotlin.components.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.eduayuso.cgkotlin.shared.presentation.CGPresenter
import dev.eduayuso.cgkotlin.shared.presentation.mvp.IViewEvents

abstract class CGFragment<E: IViewEvents>: Fragment(), IViewEvents {

    abstract val presenter: CGPresenter<E>
    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.presenter.listener = this as E?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        this.presenter.listener = this as E?
        return inflater.inflate(this.layoutResourceId, container, false)
    }

    override fun onDestroy() {

        super.onDestroy()
        this.presenter.listener = null
    }
}