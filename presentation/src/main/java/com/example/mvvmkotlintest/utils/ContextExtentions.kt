package com.example.mvvmkotlintest.utils

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.example.mvvmkotlintest.base.BaseFragment

object ContextExtentions {


    fun BaseFragment<*,*>?.hideKeyboard() {
        this?.let {
            val imm = ContextCompat.getSystemService(it.requireContext(), InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(binding.root.rootView?.windowToken, 0)
        }
    }
}