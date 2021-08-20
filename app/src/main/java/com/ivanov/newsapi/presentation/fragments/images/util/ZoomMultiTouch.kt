package com.ivanov.newsapi.presentation.fragments.images.util

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

private const val NONE = 0
private const val DRAG = 1
private const val ZOOM = 2

object ZoomMultiTouch {

    private var isZoom = false
    private var isOutSide = false
    private var mode = NONE
    private val start = PointF()
    private val mid = PointF()
    private var oldDist = 1f
    private var xCoordinate = 0f
    private var yCoordinate = 0f

    fun viewTransformation(view: View, motionEvent: MotionEvent) {
        when (motionEvent.action and MotionEvent.ACTION_MASK) {

            MotionEvent.ACTION_DOWN -> {
                xCoordinate = view.x - motionEvent.rawX
                yCoordinate = view.y - motionEvent.rawY
                start.set(motionEvent.x, motionEvent.y)
                isOutSide = false
                mode = DRAG
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(motionEvent)
                if (oldDist > 10f) {
                    midPoint(motionEvent)
                    mode = ZOOM
                }
            }

            MotionEvent.ACTION_UP -> {
                isZoom = false
                isOutSide = true
            }

            MotionEvent.ACTION_OUTSIDE -> {
                isOutSide = true
                mode = NONE
            }

            MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
            }

            MotionEvent.ACTION_MOVE -> {
                if (!isOutSide) {
                    if (mode == DRAG && view.scaleX != ZOOM_MIN) {
                        isZoom = false
                        view.animate().x(motionEvent.rawX + xCoordinate)
                            .y(motionEvent.rawY + yCoordinate).setDuration(0).start()
                    }

                    if (mode == ZOOM && motionEvent.pointerCount == 2) {
                        val newDist = spacing(motionEvent)
                        if (newDist > 10f) {
                            var scale = newDist / oldDist * view.scaleX
                            scale = max(ZOOM_MIN, min(scale, ZOOM_MAX))
                            view.scaleX = scale
                            view.scaleY = scale
                            if (scale == ZOOM_MIN) {
                                view.animate().x(ZOOM_MIN).y(ZOOM_MIN).setDuration(500).start()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun midPoint(motionEvent: MotionEvent) {
        val x = motionEvent.getX(0) - motionEvent.getX(1)
        val y = motionEvent.getY(0) - motionEvent.getY(1)
        mid[x / 2] = y / 2
    }

    private fun spacing(motionEvent: MotionEvent): Float {
        val x = motionEvent.getX(0) - motionEvent.getX(1)
        val y = motionEvent.getY(0) - motionEvent.getY(1)
        return sqrt(x * x + y * y)
    }
}