package com.example.presenter.base.adapter.recyclerview

import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base Class for [RecyclerView.Adapter]
 */
abstract class BaseAdapter<ItemType, DataBindingType : ViewBinding, ViewHolderType : BaseHolder<ItemType, DataBindingType>> :
    RecyclerView.Adapter<ViewHolderType>() {

    private val data: MutableList<ItemType> = ArrayList()

    private var itemPosition = -1

    override fun onBindViewHolder(@NonNull holder: ViewHolderType, position: Int) {
        val itemData = data[position] ?: return
        holder.setData(itemData)
        itemPosition = position
        holder.bind()
    }

    private fun addData(newData: List<ItemType>?) {
        if (newData == null) {
            return
        }
        data.addAll(newData)
        notifyItemRangeInserted(itemCount, newData.size)
    }

    fun removeData(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun changeDataWithThis(newData: List<ItemType>?) {
        clearData()
        addData(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getData(): MutableList<ItemType> {
        return data
    }

}