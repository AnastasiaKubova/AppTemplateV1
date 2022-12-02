package com.template.recyclercell

import android.view.View
import androidx.viewbinding.ViewBinding

abstract class BaseCell<B: ViewBinding> {
    abstract val layoutId: Int
    abstract fun getBinding(view: View): B
    abstract fun bind(binding: B)
}