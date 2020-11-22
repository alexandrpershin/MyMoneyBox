package com.example.minimoneybox.ui.cutomview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageButton
import com.example.minimoneybox.R
import com.example.minimoneybox.extensions.makeGone
import com.example.minimoneybox.extensions.makeVisible


class ToolbarPrimaryButton : AppCompatImageButton {
    sealed class Type(val value: Int) {
        object Back : Type(0)
        class Image(@DrawableRes val resId: Int? = null) : Type(1)
        object None : Type(2)

        companion object {
            fun from(value: Int): Type {
                return when (value) {
                    0 -> Back
                    1 -> Image()
                    2 -> None
                    else -> Back
                }
            }
        }
    }

    var type: Type = Type.None
        set(value) {
            field = value

            when (value) {
                Type.None -> {
                    makeGone()
                }
                Type.Back -> {
                    makeVisible()
                    setImageResource(R.drawable.ic_back)
                }
                is Type.Image -> {
                    makeVisible()
                    setImageResource(value.resId!!)
                }

            }
        }

    constructor(context: Context) : super(context) {
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initComponent()
        setAllListeners()
    }

    private fun initComponent() {
    }

    private fun setAllListeners() {

    }
}
