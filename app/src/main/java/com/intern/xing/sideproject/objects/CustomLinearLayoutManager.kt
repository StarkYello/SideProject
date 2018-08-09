package com.intern.xing.sideproject.objects

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager


class CustomLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean) : LinearLayoutManager(context, orientation, reverseLayout) {

    // it will always pass false to RecyclerView when calling "canScrollVertically()" method.
    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun getOrientation(): Int {
        return super.getOrientation()
    }
}