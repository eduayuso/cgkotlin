package dev.eduayuso.cgkotlin

import dev.eduayuso.cgkotlin.components.Styles
import dev.eduayuso.cgkotlin.features.convexhull.ConvexHullView
import dev.eduayuso.cgkotlin.features.segmentintersection.SegmentIntersectionView
import javafx.stage.Stage
import tornadofx.App
import tornadofx.View
import tornadofx.tabpane

class CGKotlinApp: App(MainTabsView::class, Styles::class) {

    override fun start(stage: Stage) {

        with(stage) {
            minWidth = 800.0
            minHeight = 600.0
            //isMaximized = true
            super.start(this)
        }
    }
}

class MainTabsView: View("Computational Geometry with Kotlin") {

    override val root =

            tabpane {
                tab<ConvexHullView>()
                tab<SegmentIntersectionView>()
            }
}