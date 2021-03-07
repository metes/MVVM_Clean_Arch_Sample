package com.example.mvvmkotlintest.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.example.mvvmkotlintest.R
import com.example.mvvmkotlintest.utils.CommonUtils

typealias ActivityInflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<BindingType : ViewBinding, ViewModelType : BaseViewModel>(private val inflate: ActivityInflate<BindingType>) :
    AppCompatActivity() {

    lateinit var binding: BindingType
    abstract fun initViews()
    abstract fun changeBackground(resId: Int, color: Boolean)
    protected abstract val viewModel: ViewModelType
    var onBackPressAlternative: (() -> Unit)? = null

    val isOnline: Boolean get() = CommonUtils.isOnline(baseContext)

    val currentFragment: Fragment?
        get() {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            return navHostFragment?.childFragmentManager?.fragments?.get(0)
        }

    private val loadingDialog: DialogFragment? by lazy {
        null //LoadingDialog(this, R.style.LoadingDialogStyle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate.invoke(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViews()
        subscribeLoading()

    }

    override fun onBackPressed() {
        if (onBackPressAlternative != null) {
            onBackPressAlternative!!()
        } else {
            super.onBackPressed()
        }
    }

    private fun subscribeLoading() {
        viewModel.loadingDetection.observe(this, Observer {
            if (it == true) {
                loadingDialog?.show(supportFragmentManager, null)
            } else {
                loadingDialog?.dismiss()
            }
        })
    }

    fun reloadActivity() {
        overridePendingTransition(0, 0)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }


}