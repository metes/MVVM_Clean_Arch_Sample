package com.example.presenter.base.adapter.recyclerview


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base Holder Class for [RecyclerView.ViewHolder]
 */
abstract class BaseHolder<DataType, BindingType : ViewBinding> constructor(private val viewDataBinding: BindingType) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    /**
     * Getter for [DataType] class
     */
    var item: DataType? = null

    /**
     * Set data to layout
     * @param data -> Model object
     */
    fun setData(data: DataType) {
        this.item = data
    }

    /**
     * Binds holder data
     */
    abstract fun bind()

    /**
     * Getter for Row Class item [DataType]
     */
    fun getRowItem(): DataType? {
        return item
    }

    fun getRowBinding(): BindingType? {
        return viewDataBinding
    }

    fun getRoot(): View {
        return viewDataBinding.root
    }
}