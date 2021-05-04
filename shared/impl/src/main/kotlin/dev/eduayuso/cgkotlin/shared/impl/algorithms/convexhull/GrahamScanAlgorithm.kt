package dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.impl.algorithms.AlgorithmUtil
import java.util.*

/**
 * =================================================================================================
 * Graham Scan Algorithm
 * =================================================================================================
 * Complexity O(n*logn)
 * -------------------------------------------------------------------------------------------------
 * Phase 1: Preprocessing
 * -------------------------------------------------------------------------------------------------
 * First we search the lowerAndThenLeftMost point: s0.
 * The rest of points is ordered according to the polar angle of the vector from s0 to each point.
 * We take the first of the ordered points: s1. So, s0->s1 is the first extreme edge
 * We have two stacks:
 * Stack S holds the candidate extreme points. The first elements pushed are s0 and s1
 * Stack T holds the rest of the points pushed in order
 * -------------------------------------------------------------------------------------------------
 * Phase 2: Scan
 * -------------------------------------------------------------------------------------------------
 * We take two points from the top of S and do a leftTest with the first point from top of T.
 * If test returns true -> pop from T -> push popped point to S
 * If test returns false -> pop from S (discard candidate)
 * Repeat until T is empty
 */
object GrahamScanAlgorithm: IConvexHullAlgorithm {

    override val stepOption = true

    val stackS: Stack<PointEntity> = Stack()
    val stackT: Stack<PointEntity> = Stack()

    /**
     * Vars only for steps
     */
    var stepping = false
    var s0: Int? = null // first point
    var sn: Int = 0 // stackS index
    var tn: Int = 0 // stackT index

    override fun run(input: PointSetEntity, listener: IConvexHullTaskListener) {

        this.reset()
        input.reset()

        preprocessing(input)
        while (stackT.isNotEmpty()) scan()

        // We join the last point to the first, so we close the convex hull
        stackS.lastElement().successor = stackS.firstElement()

        // Present result (all points are references, so 'input' contains the points updated as extreme)
        listener.onFinish(input)
    }

    private fun preprocessing(input: PointSetEntity) {

        // First we search the lowerAndThenLeftMost point: p0
        val s0 = AlgorithmUtil.lowerThenLeftMost(input)
        val p0 = input.list[s0]

        // The other points (all but p0) is ordered according to the polar angle of the vector from p0 to each point.
        val inputButP0 = input.list.filter { it != p0 }
        val sortedList = inputButP0.sortedWith {
            a, b -> AlgorithmUtil.polarAngleComparator(p0, a, b)
        }

        // We build a stack pushing the ordered points but
        sortedList.forEach { stackT.push(it) }

        // Our first extreme point is p0 and the first of the ordered list (p1)
        p0.apply { isExtreme = true }
        val p1 = stackT.pop().apply { isExtreme = true }

        // We have the first extreme edge: s0->s1
        // Stack S holds the candidate extreme points. The first elements pushed are s0 and s1
        // Stack T holds the rest of the points pushed in order
        stackS.push(p0)
        stackS.push(p1)
        p0.successor = p1
    }

    private fun scan() {

        // We take two points from the top of S and do a leftTest with the first point from top of T.
        val s0 = stackS.peek()
        val s1 = stackS.elementAt(stackS.size-2)
        val p  = stackT.peek()

        // If test returns true -> pop from T -> push popped point to S
        // If test returns false -> pop from S (discard candidate)
        if (AlgorithmUtil.toLeft(s1, s0, p)) {
            val point = stackT.pop()
            if (!stackS.contains(point)) {
                stackS.push(point.apply { isExtreme = true })
                s0.successor = p
            }

        } else {
            s0.successor = null
            stackS.pop().isExtreme = false
        }
    }

    /**
     * This method shows the process step by step
     */
    override fun step(input: PointSetEntity, listener: IConvexHullTaskListener) {

        if (!stepping || stackT.isEmpty()) {
            this.reset()
            input.reset()
            stepping = true
            s0 = AlgorithmUtil.lowerThenLeftMost(input)
            preprocessing(input)
        }

        val p0 = input.list[s0!!]
        val pn = stackT.peek()

        scan()

        if (!stackT.isEmpty()) {
            val helper = PointSetEntity(listOf(p0, pn))
            listener.onStep(helper, input, null)
        } else {
            // We join the last point to the first, so we close the convex hull
            stackS.lastElement().successor = stackS.firstElement()
            listener.onFinish(input)
        }
    }

    override fun reset() {

        stepping = false
        s0 = null
        sn = 0
       // tn = 0
        stackS.clear()
        stackT.clear()
    }
}