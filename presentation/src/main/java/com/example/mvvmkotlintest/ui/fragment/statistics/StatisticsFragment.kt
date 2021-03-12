package com.example.mvvmkotlintest.ui.fragment.statistics

import androidx.fragment.app.viewModels
import com.example.mvvmkotlintest.R
import com.example.mvvmkotlintest.base.BaseFragment
import com.example.mvvmkotlintest.databinding.FragmentStatisticsBinding
import com.example.mvvmkotlintest.ui.custom.IGeneralDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding, StatisticsVM>(FragmentStatisticsBinding::inflate) {

    override val backgroundResId: Int = R.color.purple_500
    override val viewModel: StatisticsVM by viewModels()

    @Inject
    lateinit var generalDialog: IGeneralDialog

    override fun onFragmentCreated() {
        "StatisticsFragment:".showAsLog()
        viewModel.getStatics()

        generalDialog.messageText = "Hilt çalıştı"
        generalDialog.titleText = "title"
        generalDialog.showDialog(childFragmentManager)


    }

    override fun subscribe() {
        viewModel.staticsListLD.observe(viewLifecycleOwner) {
            it.forEach {
                "xCases: ${it.cases}".showAsLog()
            }
        }
    }

}