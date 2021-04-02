package dev.eduayuso.cgkotlin.shared.data

import dev.eduayuso.cgkotlin.shared.data.services.IConvexHullService

interface IDataManager {

    val convexHull: IConvexHullService
}