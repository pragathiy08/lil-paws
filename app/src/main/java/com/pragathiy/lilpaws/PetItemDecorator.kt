package com.pragathiy.lilpaws

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PetItemDecorator : RecyclerView.ItemDecoration() {

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
            outRect.right = 16
        }
    }
}