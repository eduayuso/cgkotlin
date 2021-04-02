package dev.eduayuso.cgkotlin.shared.data.services

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.entities.IEntity

abstract class IDataService<IAlgorithm> {

    abstract val algorithms: Map<String, IAlgorithm> // Key: Unique algorithm name
}