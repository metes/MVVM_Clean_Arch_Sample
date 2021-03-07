package com.example.mvvmkotlintest.ui.fragment.statistics

import androidx.fragment.app.viewModels
import com.example.mvvmkotlintest.R
import com.example.mvvmkotlintest.base.BaseFragment
import com.example.mvvmkotlintest.databinding.FragmentStatisticsBinding

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding, StatisticsVM>(FragmentStatisticsBinding::inflate) {

    override val backgroundResId: Int = R.color.purple_500
    override val viewModel: StatisticsVM by viewModels()

    override fun onFragmentCreated() {

    }

}