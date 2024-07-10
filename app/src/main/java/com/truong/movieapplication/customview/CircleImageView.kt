package com.truong.movieapplication.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircleImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {
    private val paint = Paint()
    private val path = Path()

    init {
        paint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.WHITE
        }
    }

    override fun onDraw(canvas: Canvas) {
        val radius = width / 2f
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvasBitmap = Canvas(bitmap)
        super.onDraw(canvasBitmap)

        path.addCircle(radius, radius, radius, Path.Direction.CCW)
        canvas.clipPath(path)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}
