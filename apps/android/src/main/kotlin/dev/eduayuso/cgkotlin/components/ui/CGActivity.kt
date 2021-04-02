package dev.eduayuso.cgkotlin.components.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.eduayuso.cgkotlin.shared.presentation.CGPresenter
import dev.eduayuso.cgkotlin.shared.presentation.mvp.IViewEvents

abstract class CGActivity<E: IViewEvents>: AppCompatActivity(), IViewEvents {

    abstract val presenter: CGPresenter<E>
    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.presenter.listener = this as E?
        this.setContentView(layoutResourceId)
    }

    override fun onDestroy() {

        super.onDestroy()
        this.presenter.listener = null
    }
}