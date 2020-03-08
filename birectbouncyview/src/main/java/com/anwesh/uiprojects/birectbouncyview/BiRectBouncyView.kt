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
