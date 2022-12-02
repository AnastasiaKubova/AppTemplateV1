package com.template.basecomponents.utils

/**
 * Data states of fragment.
 */
sealed class UIDataState {
    object Loading: UIDataState()
    class Success<T>(data: T): UIDataState()
    class Error(error: Throwable): UIDataState()
}