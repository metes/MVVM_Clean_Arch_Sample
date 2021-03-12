package com.ikinciyenibayiagi.presenter.ui.activity

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import coil.load
import com.ikinciyenibayiagi.presenter.base.BaseActivity
import com.ikinciyenibayiagi.presenter.base.NavigateFragmentParams
import com.ikinciyenibayiagi.presenter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainActivityVM>(ActivityMainBinding::inflate) {

    override val viewModel: MainActivityVM by viewModels()

    override fun initViews() {
        // TODO("Not yet implemented")
        "MainActivity:".length
    }

    override fun changeBackground(resId: Int, color: Boolean) {
        binding.imageMainBg.load(resId)
    }

    /**
     *  Navigation'daki fragment degisim action'larini kullanabilmemi saglayan method.
     *  Opsiyonel olarak parametre alabilir
     */
    fun navigateFragment(params: NavigateFragmentParams) {
        val currentFragment = currentFragment ?: return
        NavHostFragment.findNavController(currentFragment).navigate(
            params.navAction,
            params.bundle,      // Bundle of args
            params.navOptions,  // NavOptions
            params.extras
        )
    }

}