package dev.eduayuso.cgkotlin.shared.domain.algorithms

interface IAlgorithmTaskListener<Type> {

    fun onStart(input: Type)

    fun onStep(helper: Type?, output: Type?)

    fun onFinish(output: Type)
}