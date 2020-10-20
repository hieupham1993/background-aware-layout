package com.hieupt.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import androidx.core.view.children
import com.hieupt.R
import com.hieupt.view.graphic.IClipPathCreator

/**
 * Created by HieuPT on 10/20/2020.
 */
abstract class BackgroundAwareViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), IBackgroundAwareLayout {

    override val pathCreatorMap = hashMapOf<Int, IClipPathCreator>()

    override val eraser: Paint = Paint()

    init {
        setupEraser()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        eraseChildren(canvas, children.toList())
    }

    class LayoutParams : ViewGroup.LayoutParams,
        IBackgroundAwareLayout.IBackgroundAwareLayoutParams {

        override var isBackgroundAware = false

        constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs) {
            c?.obtainStyledAttributes(attrs, R.styleable.BackgroundAwareViewGroup_Layout)?.use {
                isBackgroundAware = it.getBoolean(
                    R.styleable.BackgroundAwareViewGroup_Layout_layout_backgroundAware,
                    false
                )
            }
        }

        constructor(width: Int, height: Int) : super(width, height)
        constructor(source: ViewGroup.LayoutParams?) : super(source)
    }
}