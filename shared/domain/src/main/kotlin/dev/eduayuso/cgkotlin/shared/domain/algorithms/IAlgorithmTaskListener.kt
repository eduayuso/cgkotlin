package dev.eduayuso.cgkotlin.shared.domain.algorithms

interface IAlgorithmTaskListener<InputType, OutputType> {

    fun onStart(input: InputType)

    fun onStep(helper: InputType?, output: OutputType?, extra: Float?)

    fun onFinish(output: OutputType)
}