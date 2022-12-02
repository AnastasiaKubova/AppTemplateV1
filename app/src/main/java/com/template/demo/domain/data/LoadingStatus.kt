package com.template.demo.domain.data

sealed class LoadingStatus<T> {
    class Success<T>(val result: T): LoadingStatus<T>()
    class Loading<T>: LoadingStatus<T>()
    class Failed<T>(val errorMessage: String): LoadingStatus<T>()
}