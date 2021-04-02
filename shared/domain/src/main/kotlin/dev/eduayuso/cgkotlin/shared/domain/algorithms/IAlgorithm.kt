package dev.eduayuso.cgkotlin.shared.domain.algorithms

interface IAlgorithm<InputType, ListenerClass> {

    val stepOption: Boolean

    /**
     * Runs the algorithm completely in suspending function
     */
    fun run(input: InputType, listener: ListenerClass)

    /**
     * Runs a single step of the algoritm to see the process.
     * User click the step button to go forward the process step by step.
     */
    fun step(input: InputType, listener: ListenerClass)

    /**
     * It could be needed to reset some vars used in step running
     */
    fun reset()
}