package dev.eduayuso.cgkotlin.shared.impl

import dev.eduayuso.cgkotlin.shared.data.IDataManager
import dev.eduayuso.cgkotlin.shared.data.services.IConvexHullService

class DataManager(

    override val convexHull: IConvexHullService

): IDataManager