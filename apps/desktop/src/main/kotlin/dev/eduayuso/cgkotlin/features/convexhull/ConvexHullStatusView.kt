package dev.eduayuso.cgkotlin.features.convexhull

import javafx.scene.layout.Priority
import tornadofx.*

class ConvexHullStatusView: View() {

    override val root = vbox {

        paddingAll = 4.0
        vgrow = Priority.NEVER
    }

    fun clear() = root.clear()

    fun writeLog(message: String) {

        root.add(text(message))
    }
}