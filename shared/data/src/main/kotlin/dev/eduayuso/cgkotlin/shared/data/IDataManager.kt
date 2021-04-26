package dev.eduayuso.cgkotlin.shared.data

import dev.eduayuso.cgkotlin.shared.data.services.IConvexHullService
import dev.eduayuso.cgkotlin.shared.data.services.ISegmentIntersectionService

interface IDataManager {

    val convexHull: IConvexHullService
    val segmentIntersection: ISegmentIntersectionService
}