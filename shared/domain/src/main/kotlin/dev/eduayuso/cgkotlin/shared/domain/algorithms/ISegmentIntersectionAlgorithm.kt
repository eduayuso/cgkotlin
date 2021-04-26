package dev.eduayuso.cgkotlin.shared.domain.algorithms

import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity

interface ISegmentIntersectionAlgorithm: IAlgorithm<SegmentSetEntity, ISegmentIntersectionTaskListener>

interface ISegmentIntersectionTaskListener: IAlgorithmTaskListener<SegmentSetEntity, PointSetEntity>