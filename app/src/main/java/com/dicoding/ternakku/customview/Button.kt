package com.dicoding.ternakku.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.ternakku.R

class Button: AppCompatButton {

    private lateinit var enableBg: Drawable
    private lateinit var disableBg: Drawable
    private var textColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isEnabled) enableBg else disableBg

        setTextColor(textColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = if (isEnabled) "Submit" else "Isi Dulu"
    }

    private fun init(){
        textColor = ContextCompat.getColor(context, R.color.brown2)
        enableBg = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        disableBg = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }
}