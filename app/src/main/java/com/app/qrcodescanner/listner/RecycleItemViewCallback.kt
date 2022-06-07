package com.app.qrcodescanner.listner

import android.view.View

interface RecycleItemViewCallback<T> {
    fun onItemViewClicked(item: T?, position: Int)
    fun onItemViewClicked(item: T?, position: Int, view: View? = null) {
        onItemViewClicked(item, position)
    }
}