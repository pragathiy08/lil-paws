package com.pragathiy.lilpaws

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PetItemDecorator : RecyclerView.ItemDecoration() {

    private val _tag = "appx"

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildLayoutPosition(view)
        outRect.top = 16
        if (pos % 2 == 0) {
            outRect.left = 16
            outRect.right = 8
        } else {
            outRect.left = 9
            outRect.right = 16
        }
    }
}