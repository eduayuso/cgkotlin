package dev.eduayuso.cgkotlin.shared.data.services

abstract class IDataService<IAlgorithm> {

    abstract val algorithms: Map<String, IAlgorithm> // Key: Unique algorithm name
}