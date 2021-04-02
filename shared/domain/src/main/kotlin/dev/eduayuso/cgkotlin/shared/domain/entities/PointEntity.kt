package dev.eduayuso.cgkotlin.shared.domain.entities

class PointEntity(

    val x: Float,
    val y: Float

) {
    var isExtreme = false
    var successor: PointEntity? = null
}