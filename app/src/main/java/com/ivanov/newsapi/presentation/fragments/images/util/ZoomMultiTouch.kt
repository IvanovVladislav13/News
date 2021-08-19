package com.ivanov.newsapi.presentation.fragments.images.util

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

private const val NONE = 0
private const val DRAG = 1
private const val ZOOM = 2

class ZoomMultiTouch {
    private var lastEvent: FloatArray? = null
    private var d = 0f
    private var newRot = 0f
    private var isZoomAndRotate = false
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
                lastEvent = null
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(motionEvent)
                if (oldDist > 10f) {
                    midPoint(mid, motionEvent)
                    mode = ZOOM
                }

                lastEvent = FloatArray(4)
                lastEvent!![0] = motionEvent.getX(0)
                lastEvent!![1] = motionEvent.getX(1)
                lastEvent!![2] = motionEvent.getY(0)
                lastEvent!![3] = motionEvent.getY(1)
                d = rotation(motionEvent)
            }

            MotionEvent.ACTION_UP -> {
                isZoomAndRotate = false
            }

            MotionEvent.ACTION_OUTSIDE -> {
                isOutSide = true
                mode = NONE
                lastEvent = null
            }

            MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
                lastEvent = null
            }

            MotionEvent.ACTION_MOVE -> {
                if (!isOutSide) {
                    if (mode == DRAG) {
                        isZoomAndRotate = false
                        view.animate().x(motionEvent.rawX + xCoordinate)
                            .y(motionEvent.rawY + yCoordinate).setDuration(0).start()
                    }

                    if (mode == ZOOM && motionEvent.pointerCount == 2) {
                        val newDist = spacing(motionEvent)
                        if (newDist > 10f) {
                            var scale = newDist / oldDist * view.scaleX
                            scale = max(1f, min(scale, 7f))
                            view.scaleX = scale
                            view.scaleY = scale
                        }
                        if (lastEvent != null) {
                            newRot = rotation(motionEvent)
                            view.rotation = view.rotation + (newRot - d)
                        }
                    }
                }
            }
        }
    }

    private fun rotation(motionEvent: MotionEvent): Float {
        val deltaX = motionEvent.getX(0) - motionEvent.getX(1)
        val deltaY = motionEvent.getY(0) - motionEvent.getY(1)
        val radians = atan2(deltaY, deltaX)
        return Math.toDegrees(radians.toDouble()).toFloat()
    }

    private fun midPoint(mid: PointF, motionEvent: MotionEvent) {
        val x = motionEvent.getX(0) - motionEvent.getX(1)
        val y = motionEvent.getY(0) - motionEvent.getY(1)
        mid.set(x / 2, y / 2)
    }

    private fun spacing(motionEvent: MotionEvent): Float {
        val x = motionEvent.getX(0) - motionEvent.getX(1)
        val y = motionEvent.getY(0) - motionEvent.getY(1)
        return sqrt(x * x + y * y)
    }
}