package com.example.mvvmkotlintest.base

import android.text.Spanned
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mvvmkotlintest.BuildConfig
import com.example.mvvmkotlintest.R
import com.example.mvvmkotlintest.ui.custom.GeneralDialog

abstract class BaseToolsFragment : Fragment() {

    fun String.showAsDialog(
        titleText: String? = null,
        onCancelClicked: (() -> Unit)? = null,
        onOkClicked: (() -> Unit)? = null
    ) {
        GeneralDialog(titleText ?: "", this, null, onOkClicked, onCancelClicked).newInstance()
            .show(childFragmentManager, "")
    }

    fun Spanned.showAsDialog(
        titleText: String? = null,
        onCancelClicked: (() -> Unit)? = null,
        onOkClicked: (() -> Unit)? = null
    ) {
        GeneralDialog(titleText ?: "", null, this, onOkClicked, onCancelClicked).newInstance()
            .show(childFragmentManager, "")
    }


    fun String.showAsDialog(
        titleResId: Int? = null,
        onCancelClicked: (() -> Unit)? = null,
        onOkClicked: (() -> Unit)? = null
    ) {
        val title = if (titleResId != null) getString(titleResId) else null
        showAsDialog(title, onOkClicked, onCancelClicked)
    }


    fun Int.showAsDialog(titleResId: Int? = null, onOkClicked: (() -> Unit)? = null) {
        getString(this).showAsDialog(titleResId, onOkClicked)
    }

    fun Int.showAsToast() {
        Toast.makeText(context, getString(this), Toast.LENGTH_SHORT).show()
    }

    fun String.showAsToast() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

    fun String.showAsErrorDialog(onOkClicked: (() -> Unit)? = null) {
        showAsDialog(R.string.uyari, onOkClicked)
    }

    fun String.showAreYouSureDialog(onOkClicked: (() -> Unit)? = null) {
        showAsDialog(R.string.emin_misiniz, onOkClicked)
    }

    fun String.showAsLog() {
        if (BuildConfig.DEBUG) {
            Log.d(javaClass.simpleName, this)
        }
    }
    fun String.showAsLogE() {
        if (BuildConfig.DEBUG) {
            Log.e(javaClass.simpleName, this)
        }
    }



}