package com.template.utils

inline fun allTruth(params1: Boolean, params2: Boolean, result: (Boolean) -> Unit) {
    result(params1 && params2)
}

inline fun allTruth(params1: Boolean, params2: Boolean, params3: Boolean, result: (Boolean) -> Unit) {
    result(params1 && params2 && params3)
}

inline fun allTruth(params1: Boolean, params2: Boolean, params3: Boolean, params4: Boolean, result: (Boolean) -> Unit) {
    result(params1 && params2 && params3 && params4)
}

inline fun <T1, T2> let(params1: T1?, params2: T2?,  result: (T1, T2) -> Unit) {
    if (params1 != null && params2 != null) {
        result(params1, params2)
    }
}