package dev.eduayuso.cgkotlin.shared.domain.algorithms

import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity

interface IConvexHullAlgorithm: IAlgorithm<PointSetEntity, IConvexHullTaskListener>

interface IConvexHullTaskListener: IAlgorithmTaskListener<PointSetEntity, PointSetEntity>