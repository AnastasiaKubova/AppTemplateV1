package com.template.demo.presentation.fragment.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.template.demo.R

enum class ErrorVO(
    @DrawableRes val res: Int,
    @StringRes val messageError: Int,
    @StringRes val buttonText: Int,
    val action: (() -> Unit)? = null
) {

    NO_INTERNET(
        R.drawable.ic_sad_smile_24,
        R.string.error_loading,
        R.string.try_again,
    ),

    NETWORK_ERROR(
        R.drawable.ic_outline_wifi_off_24,
        R.string.no_internet,
        R.string.try_again,
    )
}