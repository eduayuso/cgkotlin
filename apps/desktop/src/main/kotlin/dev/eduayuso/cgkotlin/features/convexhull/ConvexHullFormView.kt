package dev.eduayuso.cgkotlin.features.convexhull

import dev.eduayuso.cgkotlin.components.Styles
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.presentation.features.convexhull.ConvexHullPresenter
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.image.ImageView
import javafx.scene.layout.Priority
import tornadofx.*

class ConvexHullFormView : View() {

    val MAX_POINTS = 10000

    lateinit var presenter: ConvexHullPresenter

    val numberOfPoints = SimpleIntegerProperty(10)
    val algorithmList = FXCollections.observableArrayList("...")

    lateinit var stepButton: Button
    lateinit var algorithmComboBox: ComboBox<String>
    lateinit var listener: IConvexHullTaskListener

    override val root = form {

        vbox {
            addClass(Styles.boxWithBorder)
            label {
                addClass(Styles.heading)
                text = "Point set (input)"
            }
            separator()
            fieldset {
                field("Number of points:") {
                    textfield(numberOfPoints) {
                        maxWidth = 48.0
                        filterInput {
                            it.controlNewText.isInt() &&
                            it.controlNewText.toInt() in 0..MAX_POINTS
                        }
                    }
                }
            }
            button("Generate") {
                graphic = ImageView("images/shuffle.png")
                useMaxWidth = true
                action {
                    presenter.setMaxPoints(numberOfPoints.value)
                    presenter.createRandomPointSet()
                }
            }
        }

        vbox {
            addClass(Styles.boxWithBorder)
            vboxConstraints {
                marginTop = 10.0
            }
            label {
                addClass(Styles.heading)
                text = "Convex hull extreme points"
            }
            separator()

            label("Select algorithm:")

            vbox {
                vboxConstraints {
                    marginBottom = 12.0
                }
                algorithmComboBox =
                    combobox<String> {
                        items = algorithmList
                        useMaxWidth = true
                    }.apply {
                        setOnAction {
                            presenter.selectAlgorithm(value)
                        }
                    }
            }
            hbox {
                spacing = 4.0
                button("Run") {
                    graphic = ImageView("images/play.png")
                    useMaxWidth = true
                    hgrow = Priority.ALWAYS
                    action {
                        presenter.findExtremePoints(listener)
                    }
                }
                stepButton =
                button("Step") {
                    graphic = ImageView("images/step.png")
                    useMaxWidth = true
                    hgrow = Priority.ALWAYS
                    action {
                        presenter.findExtremePointsByStep(listener)
                    }
                }.apply { hide()  }
            }
        }

        paddingAll = 10.0
        alignment = Pos.TOP_LEFT
    }

    fun populateAlgorithmList(list: List<String>) {

        this.algorithmList.apply {
            clear()
            addAll(list)
        }
    }
}