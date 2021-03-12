package com.ikinciyenibayiagi.presenter.ui.fragment.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import com.ikinciyenibayiagi.domain.entities.statics.StaticsResponseItem
import com.ikinciyenibayiagi.presenter.base.adapter.recyclerview.BaseAdapter
import com.ikinciyenibayiagi.presenter.base.adapter.recyclerview.BaseHolder
import com.ikinciyenibayiagi.presenter.databinding.ItemStaticsBinding

class StaticsAdapter(
        private val onClickListener: ((StaticsResponseItem?, Int, FragmentNavigator.Extras?) -> Unit)
) : BaseAdapter<StaticsResponseItem?, ItemStaticsBinding, StaticsItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticsItemHolder {
        return StaticsItemHolder(
            ItemStaticsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onClickListener
        )
    }
}


class StaticsItemHolder(
        viewDataBinding: ItemStaticsBinding,
        private val onClickListener: ((StaticsResponseItem?, Int, FragmentNavigator.Extras?) -> Unit)
) : BaseHolder<StaticsResponseItem?, ItemStaticsBinding>(viewDataBinding) {

    override fun bind() {
        getRowBinding()?.apply {
            val item = getRowItem() ?: return
            txtTitle.text = item.cases.total.toString()
            containerRoot.setOnClickListener {
//                ViewCompat.setTransitionName(txtTitle, "title_$adapterPosition")
                val extras = null //FragmentNavigatorExtras(txtTitle to "title_$adapterPosition")
                onClickListener(item, adapterPosition, extras)
            }
        }
    }


}
