package com.anwesh.uiprojects.birectbouncyview

/**
 * Created by anweshmishra on 08/03/20.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Canvas
import android.app.Activity
import android.content.Context

val colors : Array<String> = arrayOf("#3F51B5", "#f44336", "#4CAF50", "#01579B", "#009688")
val lines : Int = 2
val parts : Int = 5
val strokeFactor : Int = 90
val delay : Long = 20
val scGap : Float = 0.02f / (lines * parts)
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawBiRectBouncyLine(i : Int, scale : Float, w : Float, paint : Paint) {
    val gap : Float = w / (colors.size)
    val sf : Float = scale.sinify()
    val sfi : Float = sf.divideScale(i, parts)
    save()
    translate(gap * (i + 1), 0f)
    for (j in 0..(lines)) {
        val sfij : Float = sfi.divideScale(j, lines)
        save()
        translate(gap * j, 0f)
        rotate(-90f * j)
        drawLine(0f, 0f, gap * sfij, 0f, paint)
        restore()
    }
    restore()
}

fun Canvas.drawBiRectBouncyLines(scale : Float, w : Float, paint : Paint) {
    for (j in 0..(parts - 1)) {
        drawBiRectBouncyLine(j, scale, w, paint)
    }
}

fun Canvas.drawBRBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = Color.parseColor(colors[i])
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    save()
    translate(0f, h / 2)
    drawBiRectBouncyLines(scale, w, paint)
    restore()
}

class BiRectBouncyView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
}