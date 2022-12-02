package com.template.recyclercell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class UniversalAdapter: RecyclerView.Adapter<ViewHolderCell<ViewBinding>>() {

    private val baseList: MutableList<BaseCell<ViewBinding>> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCell<ViewBinding> {
        val binding = baseList[viewType].getBinding(
            LayoutInflater.from(parent.context).inflate(baseList[viewType].layoutId, parent, false)
        )
        return ViewHolderCell(baseList[viewType].getBinding(binding.root))
    }

    override fun onBindViewHolder(holder: ViewHolderCell<ViewBinding>, position: Int) {
        baseList[position].bind(holder.binding)
    }

    override fun getItemCount() = baseList.size

    @Suppress("UNCHECKED_CAST")
    fun updateAll(newList: List<Any>) {
        baseList.clear()
        updateLists(newList as List<BaseCell<ViewBinding>>)
    }

    @Suppress("UNCHECKED_CAST")
    fun addAll(newList: List<Any>) {
        updateLists(newList as List<BaseCell<ViewBinding>>)
    }

    private fun updateLists(newList: List<BaseCell<ViewBinding>>) {
        AdapterCellDiffUtils(baseList, newList).also {
            val productDiffResult = DiffUtil.calculateDiff(it)
            baseList.addAll(newList)
            productDiffResult.dispatchUpdatesTo(this)
        }
    }
}