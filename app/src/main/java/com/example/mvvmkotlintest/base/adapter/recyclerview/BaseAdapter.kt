package com.example.mvvmkotlintest.base.adapter.recyclerview

import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base Class for [RecyclerView.Adapter]
 */
abstract class BaseAdapter<M, DB : ViewBinding, VH : BaseHolder<M, DB>> : RecyclerView.Adapter<VH>() {

    private val data: MutableList<M> = ArrayList()


    private var itemPosition = -1

    override fun onBindViewHolder(@NonNull holder: VH, position: Int) {
        data[position]?.also {
            holder.doBindings(it)
            itemPosition = position
            holder.bind()
        }
    }

    private fun addData(newData: List<M>?) {
        if (newData == null) {
            return
        }
//        if (data == null) {
//            data = ArrayList()
//        }
        data?.addAll(newData)
        notifyItemRangeInserted(itemCount, newData.size)
    }

//    fun addData(newData: M?, position: Int) {
//        if (newData == null) {
//            return
//        }
////        if (data == null) {
////            data = ArrayList()
////        }
//        data?.add(position, newData)
//        notifyItemInserted(position)
//    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearData() {
        data?.let {
            it.clear()
            notifyDataSetChanged()
        }
    }

    fun changeDataWithThis(newData: List<M>?) {
        clearData()
        addData(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun getData(): MutableList<M>? {
        return data
    }
}