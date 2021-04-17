package dev.eduayuso.cgkotlin.shared.impl.algorithms.convexhull

import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullAlgorithm
import dev.eduayuso.cgkotlin.shared.domain.algorithms.IConvexHullTaskListener
import dev.eduayuso.cgkotlin.shared.domain.entities.PointEntity
import dev.eduayuso.cgkotlin.shared.domain.entities.PointSetEntity
import dev.eduayuso.cgkotlin.shared.impl.algorithms.AlgorithmUtil
import java.awt.Point
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

    var s0: Int? = null
    val stackS: Stack<PointEntity> = Stack()
    val stackT: Stack<PointEntity> = Stack()

    override fun run(input: PointSetEntity, listener: IConvexHullTaskListener) {

        preprocessing(input)
        while (stackT.isNotEmpty()) scan()

        for (s in 0 until stackS.size) {
            if (s < stackS.size-1) {
                stackS[s].successor = stackS[s + 1]
            } else {
                stackS[s].successor = stackS[0]
            }
        }

        val extremePoints = PointSetEntity(stackS.toList())
        listener.onFinish(extremePoints)
    }

    private fun preprocessing(input: PointSetEntity) {

        val s0 = AlgorithmUtil.lowerThenLeftMost(input)
        val sortedList = input.list.sortedWith {
            a, b -> AlgorithmUtil.polarAngleComparator(input.list[s0], a, b)
        }

        sortedList.forEach { stackT.push(it) }
        stackS.push(input.list[s0].apply { isExtreme = true })
        stackS.push(stackT.pop()) // s1
    }

    private fun scan() {

        val s0 = stackS.peek()
        val s1 = stackS.elementAt(stackS.size-2)
        val p  = stackT.peek()

        if (AlgorithmUtil.toLeft(s1, s0, p)) {
            val point = stackT.pop().apply { isExtreme = true }
            stackS.push(point)

        } else {
            stackS.pop()
        }
    }

    override fun step(input: PointSetEntity, listener: IConvexHullTaskListener) {

        if (stackS.isEmpty() && stackT.isEmpty()) {

            s0 = AlgorithmUtil.lowerThenLeftMost(input)
            val sortedList = input.list.sortedWith {
                    a, b -> AlgorithmUtil.polarAngleComparator(input.list[s0!!], a, b)
            }

            sortedList.forEach { stackT.push(it) }
        }

        val p0 = input.list[s0!!]
        val pn = stackT.pop()

        val helper = PointSetEntity(listOf(p0, pn))

        listener.onStep(helper, input)
    }

    override fun reset() {

        stackS.clear()
        stackT.clear()
    }
}