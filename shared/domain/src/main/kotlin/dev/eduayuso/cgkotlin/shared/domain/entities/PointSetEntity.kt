package dev.eduayuso.cgkotlin.shared.domain.entities

open class PointSetEntity(

    val list: List<PointEntity>

): IEntity {

    fun reset() {

        list.forEach {
            it.isExtreme = false
            it.successor = null
        }
    }
}