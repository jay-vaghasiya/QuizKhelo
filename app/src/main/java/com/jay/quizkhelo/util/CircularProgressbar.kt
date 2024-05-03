package com.jay.quizkhelo.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


import android.os.CountDownTimer


class CountdownCircularProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var maxTimeMillis = 30000L
    private var remainingTimeMillis = maxTimeMillis
    private var progressColor = Color.BLUE
    private var textColor = Color.BLACK

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 24f
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        textSize = 100f
        textAlign = Paint.Align.CENTER
    }
    private val rectF = RectF()

    private lateinit var countdownTimer: CountDownTimer

    init {
        startCountdown()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            val centerX = width / 2f
            val centerY = height / 2f
            val radius = (width - paint.strokeWidth) / 2f

            paint.color = progressColor
            rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
            drawArc(rectF, -90f, 360f * remainingTimeMillis / maxTimeMillis, false, paint)

            val text = "${remainingTimeMillis / 1000}"
            drawText(
                text,
                centerX,
                centerY - (textPaint.descent() + textPaint.ascent()) / 2,
                textPaint
            )
        }
    }

    private fun startCountdown() {
        countdownTimer = object : CountDownTimer(maxTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeMillis = millisUntilFinished
                invalidate()
            }

            override fun onFinish() {
                remainingTimeMillis = 0
                invalidate()
            }
        }
        countdownTimer.start()
    }

    fun setMaxTimeMillis(maxTimeMillis: Long) {
        this.maxTimeMillis = maxTimeMillis
        remainingTimeMillis = maxTimeMillis
        countdownTimer.cancel()
        startCountdown()
        invalidate()
    }

    fun setProgressColor(color: Int) {
        this.progressColor = color
        paint.color = color
        invalidate()
    }

    fun setTextColor(color: Int) {
        this.textColor = color
        textPaint.color = color
        invalidate()
    }
}
