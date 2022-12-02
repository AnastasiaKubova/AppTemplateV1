package com.template.basecomponents.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.template.basecomponents.R
import com.template.basecomponents.databinding.ViewInputEditTextBinding

@SuppressLint("Recycle", "CustomViewStyleable")
class ViewInputEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = ViewInputEditTextBinding.inflate(LayoutInflater.from(context),this)

    var text: String
        get() = binding.inputEditText.text.toString()
        set(value) {
            binding.inputEditText.setText(value)
        }

    var hint
        get() = binding.inputEditTextLayout.hint
        set(value) {
            binding.inputEditTextLayout.hint = value
        }

    var inputType
        get() = binding.inputEditText.inputType
        set(value) {
            binding.inputEditText.inputType = value
        }

    var macLines
        get() = binding.inputEditText.maxLines
        set(value) {
            binding.inputEditText.maxLines = value
        }

    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.viewInputEditText)) {
            getString(R.styleable.viewInputEditText_android_hint)?.let {
                hint = it
            }
            getInt(R.styleable.viewInputEditText_android_maxLength, 1).also {
                macLines = it
            }
            getString(R.styleable.viewInputEditText_android_text)?.let {
                text = it
            }
            getInt(
                R.styleable.viewInputEditText_android_inputType,
                EditorInfo.TYPE_NULL
            ).takeIf { it != EditorInfo.TYPE_NULL }?.also {
                inputType = it
            }
        }
    }

    fun showOrHideError(@StringRes message: Int?) {
        binding.inputEditTextLayout.isErrorEnabled = message != null
        message?.let { binding.inputEditTextLayout.error = context.getString(it) }
    }
}

fun ViewInputEditText.isEditTextValid(
    @StringRes messageError: Int?,
): Boolean {
    val isValid = text.isNotEmpty()
    if (isValid) showOrHideError(null) else showOrHideError(messageError)
    return isValid
}

/**
 * Compare passwords and show or hide error.
 */
fun ViewInputEditText.isEquals(
    secondEditText: ViewInputEditText,
    @StringRes messageError: Int?,
): Boolean {
    val isSame = text.trim() == secondEditText.text.trim()
    if (isSame) showOrHideError(null) else showOrHideError(messageError)
    return isSame
}