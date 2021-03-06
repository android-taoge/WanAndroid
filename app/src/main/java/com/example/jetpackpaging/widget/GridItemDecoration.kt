package com.example.jetpackpaging.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration : RecyclerView.ItemDecoration {

    private var mSizeGridSpacingPx: Int = 0
    private var mGridSize: Int = 0

    private var mNeedLeftSpacing = false

    constructor(gridColumn: Int, gridSpacing: Int) {
        mGridSize = gridColumn
        mSizeGridSpacingPx = gridSpacing
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val padding: Float =
            parent.width / mGridSize - (((parent.width - mSizeGridSpacingPx * (mGridSize - 1)) / mGridSize).toFloat())
        val itemPosition =
            (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < mGridSize) {
            outRect.top = 0
        } else {
            outRect.top = mSizeGridSpacingPx
        }
        if (itemPosition % mGridSize === 0) {
            outRect.left = 0
            outRect.right = padding.toInt()
            mNeedLeftSpacing = true
        } else if ((itemPosition + 1) % mGridSize === 0) {
            mNeedLeftSpacing = false
            outRect.right = 0
            outRect.left = padding.toInt()
        } else if (mNeedLeftSpacing) {
            mNeedLeftSpacing = false
            outRect.left = (mSizeGridSpacingPx - padding).toInt()
            if ((itemPosition + 2) % mGridSize === 0) {
                outRect.right = (mSizeGridSpacingPx - padding).toInt()
            } else {
                outRect.right = mSizeGridSpacingPx / 2
            }
        } else if ((itemPosition + 2) % mGridSize === 0) {
            mNeedLeftSpacing = false
            outRect.left = mSizeGridSpacingPx / 2
            outRect.right = (mSizeGridSpacingPx - padding).toInt()
        } else {
            mNeedLeftSpacing = false
            outRect.left = mSizeGridSpacingPx / 2
            outRect.right = mSizeGridSpacingPx / 2
        }
        outRect.bottom = 0
    }

}