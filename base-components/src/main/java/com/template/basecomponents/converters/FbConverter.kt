package com.template.basecomponents.converters

interface FbConverter<F, T> {
    fun convert(id: String, data: F) : T
}