package com.app.qrcodescanner.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet

import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView


/**
 * Created by salmonzhang on 2017/7/11
 * Customize a TextView to implement left-right click monitoring
 */
class CustomTextView(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context!!, attrs, defStyleAttr) {
    //Define a right-hand back listening method
    var drawableRightClick: DrawableRightClickListener? = null
        private set

    //Define a method to return to listening on the left
    var drawableLeftClick: DrawableLeftClickListener? = null
        private set
    val DRAWABLE_LEFT = 0 //Left picture
    val DRAWABLE_TOP = 1 //Picture above
    val DRAWABLE_RIGHT = 2 //Right picture
    val DRAWABLE_BOTTOM = 3 //The following picture

    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : this(context, attrs, 0) {}

    //Define an interface to listen on the right
    interface DrawableRightClickListener {
        fun onDrawableRightClickListener(view: View?)
    }

    //Define an interface to listen on the left
    interface DrawableLeftClickListener {
        fun onDrawableLeftClickListener(view: View?)
    }

    //Define a set method for right listening
    fun setDrawableRightClickListener(listener: DrawableRightClickListener?) {
        drawableRightClick = listener
    }

    //Define a set method for left listening
    fun setDrawableLeftClickListener(listener: DrawableLeftClickListener?) {
        drawableLeftClick = listener
    }

    //Rewriting onTouchEvent Method
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (drawableRightClick != null) {
                    val drawableRight: Drawable = getCompoundDrawables().get(DRAWABLE_RIGHT)
                    //The judgment is based on obtaining that the x value of the click area relative to the screen is greater than the rightmost boundary of the control minus the width of the right control.
                    if (drawableRight != null && event.rawX >= getRight() - drawableRight.bounds.width()) {
                        drawableRightClick!!.onDrawableRightClickListener(this)
                        return true //The return value must be true, otherwise it cannot respond.
                    }
                }
                if (drawableLeftClick != null) {
                    val drawableLeft: Drawable = getCompoundDrawables().get(DRAWABLE_LEFT)
                    //The judgment is based on the fact that the x value of the click area relative to the screen is less than the leftmost boundary of the control plus the width of the left control.
                    if (drawableLeft != null && event.rawX <= getLeft() + drawableLeft.bounds.width()) {
                        drawableLeftClick!!.onDrawableLeftClickListener(this)
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }
}