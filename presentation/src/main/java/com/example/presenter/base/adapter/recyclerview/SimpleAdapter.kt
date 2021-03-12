package com.example.presenter.base.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presenter.databinding.ItemSelectionBinding

open class SimpleAdapter(private val onClickListener: ((String) -> Unit)?) :
    BaseAdapter<String, ItemSelectionBinding, SimpleItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemHolder {
        return SimpleItemHolder(
            ItemSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickListener
        )
    }
}

class SimpleItemHolder(
    viewDataBinding: ItemSelectionBinding,
    private val onClickListener: ((String) -> Unit)?
) : BaseHolder<String, ItemSelectionBinding>(viewDataBinding) {

    override fun bind() {
        getRowBinding()?.apply {
            item = getRowItem() ?: "-"
//            ViewCompat.setTransitionName(imgEvent, "image_$adapterPosition")
//            ViewCompat.setTransitionName(txtTitle, "title_$adapterPosition")
//            getRowBinding()?.txtTitle?.text = getRowItem()?: ""
            txtTitle.text = getRowItem()?: "-"
            containerRoot.setOnClickListener { onClickListener?.let { it(getRowItem() ?: "-") } }
//        }
        }
    }
}



fun RecyclerView.prepareSimple(
    alreadySelectedItem: String?,
    itemList: List<String>?,
    onClickListener: ((String) -> Unit)? = null
) {
//  todo
    val simpleAdapter = SimpleAdapter(onClickListener)
    layoutManager = LinearLayoutManager(context)
    adapter = simpleAdapter//
    simpleAdapter.changeDataWithThis(itemList)
}
