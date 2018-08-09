package com.intern.xing.sideproject.objects

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import java.util.jar.Attributes

class SqaureCardView(context: Context,attrs:AttributeSet) : CardView(context,attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}