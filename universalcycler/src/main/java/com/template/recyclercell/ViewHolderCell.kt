package com.template.recyclercell

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

class ViewHolderCell<B: ViewBinding>(val binding: B): ViewHolder(binding.root)