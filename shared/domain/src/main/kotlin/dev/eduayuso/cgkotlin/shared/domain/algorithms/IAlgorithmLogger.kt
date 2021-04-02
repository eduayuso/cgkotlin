package dev.eduayuso.cgkotlin.shared.domain.algorithms

import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity

interface IAlgorithmLogger {

    fun write(message: String)
    fun draw(points: PointEntity)
}