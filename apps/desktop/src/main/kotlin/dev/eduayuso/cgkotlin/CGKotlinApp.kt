package dev.eduayuso.cgkotlin

import dev.eduayuso.cgkotlin.components.Styles
import dev.eduayuso.cgkotlin.features.convexhull.ConvexHullView
import javafx.stage.Stage
import tornadofx.App

class CGKotlinApp: App(ConvexHullView::class, Styles::class) {

    override fun start(stage: Stage) {

        with(stage) {
            minWidth = 800.0
            minHeight = 600.0
            //isMaximized = true
            super.start(this)
        }
    }
}