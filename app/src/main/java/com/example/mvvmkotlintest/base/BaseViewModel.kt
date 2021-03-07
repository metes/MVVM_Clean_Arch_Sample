package com.example.mvvmkotlintest.base

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import com.example.mvvmkotlintest.R
import com.example.mvvmkotlintest.utils.SingleLiveEvent


class NavigateFragmentParams(
    var navAction: Int,
    var bundle: Bundle?,
    var navOptions: NavOptions?,
    var extras: FragmentNavigator.Extras?
) // todo delete

typealias DialogParams = Triple<String, String, (() -> Unit)?>

open class BaseViewModel(private val myApp: Application) : AndroidViewModel(myApp) {

    // val sharedPref by inject<SharedPrefHelper>()

    // val userInfo get() = sharedPref.loadUser()


    /**
     *  Hata olmasi durumunda UI'da kullanmak uzere tanimlanan SingleLiveEvent
     */
    val networkErrorDetection by lazy { SingleLiveEvent<String>() }

    /**
     *  Hata olmasi durumunda UI'da kullanmak uzere tanimlanan SingleLiveEvent
     */
    val navigateFragmentDetection by lazy { SingleLiveEvent<NavigateFragmentParams>() }

    /**
     *  Request oncesinde true sonrasinda false olan SingleLiveEvent
     */
    val loadingDetection by lazy { SingleLiveEvent<Boolean>() }

    /**
     *  Request oncesinde true sonrasinda false olan SingleLiveEvent
     */
    val dialogDetection by lazy { SingleLiveEvent<DialogParams>() }

    /**
     *  Uygulamayi yeniden baslatir
     */
    val reloadAppDetection by lazy { SingleLiveEvent<Boolean>() }

    /**
     *  Telefon aramasi yaptirir
     */
    val makeCallDetection by lazy { SingleLiveEvent<String>() }

    /**
     *  Retrofit'den donen hatalar burada yakalaniyor. Exception messaji kullaniciya gosterebilir veya ozellestirilebilir
     */
    fun throwError(errorMessage: String?) {
        networkErrorDetection.postValue(errorMessage)
    }

    protected fun String.showAsErrorDialog() {
        dialogDetection.postValue(DialogParams(this, myApp.getString(R.string.hata), null))
    }

    protected fun String.showAsDialog(title: String? = null) {
        dialogDetection.postValue(DialogParams(this, title ?: myApp.getString(R.string.bilgi), null))
    }

    protected fun <T> LiveData<T>.toMutable(): MutableLiveData<T> {
        return (this as MutableLiveData)
    }

    fun reloadActivity(clearSharedPref: Boolean? = false) {
        if (clearSharedPref == true) {
//            sharedPref.clearAllData()
        }
        reloadAppDetection.postValue(true)
    }

    fun navigateFragment(
        navAction: Int,
        bundle: Bundle? = null,
        navOptions: NavOptions? = null,
        extras: FragmentNavigator.Extras? = null
    ) {
        val params = NavigateFragmentParams(navAction, bundle, navOptions, extras)
        navigateFragmentDetection.postValue(params)
    }

    fun makeCall(phoneNumber: String) {
        makeCallDetection.value = phoneNumber
    }



}
