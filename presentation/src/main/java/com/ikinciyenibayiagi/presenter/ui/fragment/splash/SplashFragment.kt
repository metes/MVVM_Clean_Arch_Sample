package com.ikinciyenibayiagi.presenter.ui.fragment.splash

import androidx.fragment.app.viewModels
import com.ikinciyenibayiagi.presenter.R
import com.ikinciyenibayiagi.presenter.base.BaseFragment
import com.ikinciyenibayiagi.presenter.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM>(FragmentSplashBinding::inflate) {

    override val backgroundResId: Int = R.color.purple_500
    override val viewModel: SplashVM by viewModels()

    override fun onFragmentCreated() {

    }

}