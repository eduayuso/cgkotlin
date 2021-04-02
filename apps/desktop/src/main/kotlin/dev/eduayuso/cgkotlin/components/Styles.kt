package dev.eduayuso.cgkotlin.components

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {

    companion object {

        val heading by cssclass()
        val boxWithBorder by cssclass()
        val paneBackground by cssclass()
    }

    init {

        label {
            padding = box(4.px)
        }

        label and heading {
            fontSize = 12.px
            fontWeight = FontWeight.BOLD
        }

        boxWithBorder {

            borderWidth += box(1.px)
            borderColor += box(Color.LIGHTGRAY)
            borderRadius += box(4.px)
            padding = box(12.px)
        }

        paneBackground {
            backgroundColor += Color.LIGHTGRAY
        }
    }
}