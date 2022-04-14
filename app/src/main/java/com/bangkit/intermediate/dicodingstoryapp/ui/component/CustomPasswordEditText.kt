package com.bangkit.intermediate.dicodingstoryapp.ui.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.intermediate.dicodingstoryapp.R

class CustomPasswordEditText : AppCompatEditText, View.OnTouchListener {
    private lateinit var toggleButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        init()
    }

    private fun init() {
        hint = resources.getString(R.string.password_hint)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        imeOptions = EditorInfo.IME_ACTION_DONE
        inputType = 129

        toggleButtonImage =
            ContextCompat.getDrawable(context, R.drawable.ic_toggle_password) as Drawable

        setCompoundDrawablesWithIntrinsicBounds(null, null, toggleButtonImage, null)
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.toString().isNotEmpty() && s.length < 6) {
                    error = resources.getString(R.string.password_invalid)
                }
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] == null) return false
        val toggleButtonStart: Float
        val toggleButtonEnd: Float
        var isToggleButtonClicked = false

        if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            toggleButtonEnd = (toggleButtonImage.intrinsicWidth + paddingStart).toFloat()
            when {
                event.x < toggleButtonEnd -> isToggleButtonClicked = true
            }
        } else {
            toggleButtonStart = (width - paddingEnd - toggleButtonImage.intrinsicWidth).toFloat()
            when {
                event.x > toggleButtonStart -> isToggleButtonClicked = true
            }
        }

        if (!isToggleButtonClicked) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                toggleButtonImage =
                    ContextCompat.getDrawable(context, R.drawable.ic_toggle_password) as Drawable

                return true
            }
            MotionEvent.ACTION_UP -> {
                toggleButtonImage =
                    ContextCompat.getDrawable(context, R.drawable.ic_toggle_password) as Drawable

                inputType = if (inputType == 129) InputType.TYPE_TEXT_VARIATION_PASSWORD
                else 129

                return true
            }
            else -> return true
        }
    }
}