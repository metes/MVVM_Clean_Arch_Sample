package com.example.presenter.ui.fragment.splash

import androidx.fragment.app.viewModels
import com.example.presenter.R
import com.example.presenter.base.BaseFragment
import com.example.presenter.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM>(FragmentSplashBinding::inflate) {

    override val backgroundResId: Int = R.color.purple_500
    override val viewModel: SplashVM by viewModels()

    override fun onFragmentCreated() {

    }

}