package com.template.utils

import android.text.format.DateFormat
import java.util.*

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Long?.orZero(): Long {
    return this ?: 0L
}

fun Float?.orZero(): Float {
    return this ?: 0F
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Long.toTimeFormat(): String {
    return DateFormat.format(TIME_FORMAT_DD_MM_YYYY, Date(this)).toString()
}

private const val TIME_FORMAT_DD_MM_YYYY = "dd MMMM yyyy"