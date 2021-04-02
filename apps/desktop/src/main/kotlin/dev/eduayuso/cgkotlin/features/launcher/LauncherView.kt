package dev.eduayuso.cgkotlin.features.launcher

import dev.eduayuso.cgkotlin.features.convexhull.ConvexHullView
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.vbox

class LauncherView: View("Computational Geometry with Kotlin") {

    override val root = vbox {

        button {
            text = "Convex Hull"
            action {
                replaceWith(ConvexHullView())
            }
        }
    }
}
