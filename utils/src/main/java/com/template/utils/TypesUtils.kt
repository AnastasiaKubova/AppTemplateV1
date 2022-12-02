package com.template.utils

import java.util.Calendar

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