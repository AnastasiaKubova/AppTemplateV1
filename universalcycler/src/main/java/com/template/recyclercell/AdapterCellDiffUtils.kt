package com.template.recyclercell

import androidx.recyclerview.widget.DiffUtil
import com.template.utils.orZero

class AdapterCellDiffUtils(
    private val oldList: List<BaseCell<*>>? = null,
    private val newList: List<BaseCell<*>>? = null
): DiffUtil.Callback() {

    override fun getOldListSize() = oldList?.size.orZero()

    override fun getNewListSize() = newList?.size.orZero()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.getOrNull(oldItemPosition) === newList?.getOrNull(newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.getOrNull(oldItemPosition)?.equals(newList?.getOrNull(newItemPosition)) ?: false
    }
}