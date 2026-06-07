package com.example.remotepc

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat

class StatusIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Визначаємо стани індикатора
    enum class Status {
        ACTIVE, INACTIVE, PENDING
    }

    private var currentStatus = Status.INACTIVE
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ripplePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var rippleRadius = 0f
    private var rippleAlpha = 0
    private var animator: ValueAnimator? = null

    init {
        setupAnimation()
    }

    private fun setupAnimation() {
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1500
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { anim ->
                val progress = anim.animatedValue as Float
                rippleRadius = (width / 2f) * progress
                rippleAlpha = ((1 - progress) * 150).toInt()
                invalidate()
            }
            start()
        }
    }

    // Новий метод для встановлення статусу
    fun setStatus(status: Status) {
        currentStatus = status
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val baseRadius = width / 4f

        // Вибір кольору залежно від статусу
        val colorRes = when (currentStatus) {
            Status.ACTIVE -> R.color.status_active
            Status.INACTIVE -> R.color.status_inactive
            Status.PENDING -> R.color.status_pending
        }

        val color = ContextCompat.getColor(context, colorRes)

        // Малюємо хвилю (ripple)
        ripplePaint.color = color
        ripplePaint.alpha = rippleAlpha
        ripplePaint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, rippleRadius, ripplePaint)

        // Малюємо основне коло
        paint.color = color
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, baseRadius, paint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}