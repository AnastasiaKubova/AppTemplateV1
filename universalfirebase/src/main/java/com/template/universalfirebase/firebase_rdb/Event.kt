package com.template.universalfirebase.firebase_rdb

sealed class Event<T> {

    class Added<T>(val data: T) : Event<T>()

    class Removed<T>(val data: T): Event<T>()
}
