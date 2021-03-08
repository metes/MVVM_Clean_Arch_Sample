package com.example.mvvmkotlintest.ui.fragment.splash

import androidx.fragment.app.viewModels
import com.example.mvvmkotlintest.R
import com.example.mvvmkotlintest.base.BaseFragment
import com.example.mvvmkotlintest.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM>(FragmentSplashBinding::inflate) {

    override val backgroundResId: Int = R.color.purple_500
    override val viewModel: SplashVM by viewModels()

    override fun onFragmentCreated() {

    }

}