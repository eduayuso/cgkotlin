package dev.eduayuso.cgkotlin.shared.impl.algorithms.segmentintersection

import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.ISegmentIntersectionTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.SegmentSetEntity
import dev.eduayuso.cgkotlin.shared.impl.algorithms.AlgorithmUtil
import dev.eduayuso.cgkotlin.shared.impl.algorithms.segmentintersection.SweepLineAlgorithm.sweepLine
import java.util.*
import javax.swing.text.Segment





/**
 * The main idea of the Bentley–Ottmann algorithm is to use a sweep line approach, in which a
 * vertical line L moves from left to right (or, e.g., from top to bottom) across the plane,
 * intersecting the input line segments in sequence as it moves.
 * The algorithm is described most easily in its general position, meaning:
 * - No two line segment endpoints or crossings have the same x-coordinate
 * - No line segment endpoint lies upon another line segment
 * - No three line segments intersect at a single point.
 * In such a case, L will always intersect the input line segments in a set of points whose vertical
 * ordering changes only at a finite set of discrete events. Specifically, a discrete event can
 * either be associated with an endpoint (left or right) of a line-segment or intersection point of
 * two line-segments. Thus, the continuous motion of L can be broken down into a finite sequence of
 * steps, and simulated by an algorithm that runs in a finite amount of time.
 * There are two types of events that may happen during the course of this simulation. When L sweeps
 * across an endpoint of a line segment s, the intersection of L with s is added to or removed from
 * the vertically ordered set of intersection points. These events are easy to predict, as the
 * endpoints are known already from the input to the algorithm. The remaining events occur when L
 * sweeps across a crossing between (or intersection of) two line segments s and t.
 * These events may also be predicted from the fact that, just prior to the event, the points of
 * intersection of L with s and t are adjacent in the vertical ordering of the intersection points.
 * The Bentley–Ottmann algorithm itself maintains data structures representing the current vertical
 * ordering of the intersection points of the sweep line with the input line segments, and a
 * collection of potential future events formed by adjacent pairs of intersection points.
 * It processes each event in turn, updating its data structures to represent the new set of
 * intersection points.
 */
object SweepLineAlgorithm: ISegmentIntersectionAlgorithm {

    override val stepOption = true

    /**
     * ---------------------------------------------------------------------------------------------
     * Event Queue
     * ---------------------------------------------------------------------------------------------
     * - Need to keep events sorted: – Lexicographic order (first by x-coordinate, and if two events
     *   have same x-coordinate then by y-coordinate)
     * - Need to be able to remove next point, and insert new points in O(logn) time.
     */
    @Suppress("NewApi")
    val eventQueue = PriorityQueue(EventComparator())

    /**
     * ---------------------------------------------------------------------------------------------
     * Sweep Line Status
     * ---------------------------------------------------------------------------------------------
     * - Store segments that intersect the sweep line l, ordered along the intersection with l .
     * - Need to insert, delete, and find adjacent neighbor in O(log n) time
     * - Use balanced binary search tree, storing the order in which segments intersect l in leaves
     */
    val lineStatus = TreeSet(SegmentComparator())

    // The sweepLine is vertical line defined by its x-coordinate
    var sweepLine: Float = 0f

    /**
     * Intersection points
     */
    val intersections = emptyList<PointEntity>().toMutableList()

    override fun run(input: SegmentSetEntity, listener: ISegmentIntersectionTaskListener) {

        this.preprocessing(input)
        while (eventQueue.isNotEmpty()) this.process()
        listener.onFinish(PointSetEntity(intersections))
    }

    /**
     * The event queue initialization consist in insert the start and end points of each segment.
     * This queue is implemented with a PriorityQueue which inserts each points so that it remains
     * ordered according to the x-coordinate of the points
     */
    private fun preprocessing(input: SegmentSetEntity) {

        this.reset()

        input.list.forEach { segment ->

            Event(segment.a, listOf(segment), EventType.startSegmentPoint).let { startPoint ->
                eventQueue.add(startPoint)
            }
            Event(segment.b, listOf(segment), EventType.endSegmentPoint).let { endPoint ->
                eventQueue.add(endPoint)
            }
        }
    }

    private fun process() {

        // Retrieve the first event
        val event = eventQueue.poll()

        // The sweepLine positions at the event.x
        this.sweepLine = event.value

        recalculateSegmentValues()

        // There are 3 event types:
        when (event.type) {
            EventType.startSegmentPoint -> processStartSegmentPoint(event)
            EventType.endSegmentPoint -> processEndSegmentPoint(event)
            EventType.intersection -> processIntersection(event)
        }
    }

    private fun processStartSegmentPoint(event: Event) {

        event.segments?.first()?.let { segment ->

            segment.calculateValue(sweepLine)

            // We add the segment to the line status
            this.lineStatus.add(segment)

            // Check intersection between segment and left neighbour
            this.lineStatus.lower(segment)?.let { left ->
                testIntersection(left, segment)
            }

            // Check intersection between segment and right neighbour
            this.lineStatus.higher(segment)?.let { right ->
                testIntersection(segment, right)
            }
        }
    }

    private fun recalculateSegmentValues() {

        this.lineStatus.forEach {
            it.calculateValue(sweepLine)
        }
    }

    private fun testIntersection(s1: SegmentEntity, s2: SegmentEntity) {

        AlgorithmUtil.findIntersection(s1, s2)?.let {
            val event = Event(it, listOf(s1, s2), EventType.intersection)
            this.eventQueue.add(event)
        }
    }

    private fun processEndSegmentPoint(event: Event) {

        event.segments?.first()?.let { segment ->

            // We remove the segment to the line status
            this.lineStatus.remove(segment)

            // Check intersection between left and right neighbours, which they now have became
            // neighbours, because segment between them has been removed
            val left = this.lineStatus.lower(segment)
            val right = this.lineStatus.higher(segment)
            if (left != null && right != null) {
                testIntersection(left, right)
            }
        }
    }

    private fun processIntersection(event: Event) {

        val left = event.segments?.first()
        val right = event.segments?.get(1)
        if (left != null && right != null) {
            this.intersections.add(event.point)
            this.swapSegments(left, right)
        }
    }

    private fun swapSegments(s1: SegmentEntity, s2: SegmentEntity) {

        this.lineStatus.remove(s1)
        this.lineStatus.remove(s2)
        val v1 = s1.value
        s1.value = s2.value
        s2.value = v1
        this.lineStatus.add(s1)
        this.lineStatus.add(s2)
    }

    override fun step(input: SegmentSetEntity, listener: ISegmentIntersectionTaskListener) {

        if (eventQueue.isEmpty()) {
            this.preprocessing(input)
        }
        this.process()

        val output = PointSetEntity(intersections)
        listener.onStep(input, output, sweepLine)
        if (eventQueue.isEmpty()) {
            listener.onFinish(output)
            this.reset()
        }
    }

    override fun reset() {

        eventQueue.clear()
        lineStatus.clear()
        intersections.clear()
        sweepLine = 0f
    }
}

object EventType {

    const val startSegmentPoint = 0
    const val endSegmentPoint = 1
    const val intersection = 2
}

data class Event(
        val point: PointEntity,
        val segments: List<SegmentEntity>? = null,
        val type: Int
) {

    val value get() = point.x
}

class EventComparator: Comparator<Event> {

    override fun compare(o1: Event, o2: Event): Int {

        return when {
            o1.value < o2.value -> -1
            o1.value > o2.value -> 1
            else -> 0
        }
    }
}

class SegmentComparator: Comparator<SegmentEntity> {

    override fun compare(o1: SegmentEntity, o2: SegmentEntity): Int {

        return when {
            o1.value < o2.value -> -1
            o1.value > o2.value -> 1
            else -> 0
        }
    }
}