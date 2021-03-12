package com.example.presenter.ui.fragment.statistics

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.statics.StaticsResponseItem
import com.example.presenter.R
import com.example.presenter.base.BaseFragment
import com.example.presenter.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment :
    BaseFragment<FragmentStatisticsBinding, StatisticsVM>(FragmentStatisticsBinding::inflate) {

    override val backgroundResId: Int = R.color.purple_500
    override val viewModel: StatisticsVM by viewModels()

    var staticsAdapter: StaticsAdapter? = StaticsAdapter(::onClickAction)

    override fun onFragmentCreated() {
        viewModel.getStatics()
    }

    override fun initBindingViews() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getStatics()
        }
        prepareRecyclerView()
    }

    override fun subscribe() {
        viewModel.staticsListLD.observe(viewLifecycleOwner) {
            staticsAdapter?.changeDataWithThis(it)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun prepareRecyclerView() {
        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = staticsAdapter
        }
    }

    private fun onClickAction(
        item: StaticsResponseItem?,
        index: Int,
        extras: FragmentNavigator.Extras?
    ) {
        "Details will be add".showAsErrorDialog { }
    }

}