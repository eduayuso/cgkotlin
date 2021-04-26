package dev.eduayuso.cgkotlin.shared.impl

import dev.eduayuso.cgkotlin.shared.data.IDataManager
import dev.eduayuso.cgkotlin.shared.data.services.IConvexHullService
import dev.eduayuso.cgkotlin.shared.data.services.ISegmentIntersectionService

class DataManager(

    override val convexHull: IConvexHullService,
    override val segmentIntersection: ISegmentIntersectionService

): IDataManager