package com.example.mvvmkotlintest.base

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.transition.TransitionInflater
import androidx.viewbinding.ViewBinding
import androidx.work.*
import com.example.mvvmkotlintest.R
import com.example.mvvmkotlintest.utils.CommonUtils
import com.example.mvvmkotlintest.utils.ContextExtentions.hideKeyboard


typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<BindingType : ViewBinding, out ViewModelType : BaseViewModel>
    (private val inflate: FragmentInflate<BindingType>) : BaseToolsFragment() {

    /**
     * ViewDataBinding tipindeki generic type, child'dan gonderiliyor.
     */
    lateinit var binding: BindingType

    /**
     * BaseViewModel tipindeki generic type, child'dan gonderiliyor
     */
    abstract val viewModel: ViewModelType

    /**
     *  Fragment'a ozel view duzenlemeleri burada yapiliyor
     */
    abstract fun onFragmentCreated()

    open var toolbarVisibility: Boolean? = true
    open val backgroundResId: Int = android.R.color.background_light
    open val isSharedViewEnabled = false
    open var onBackAlternative: (() -> Unit)? = null

    private val TAG by lazy { javaClass.simpleName }
    private val baseActivity by lazy { activity as BaseActivity<*, *>? }

    val isOnline: Boolean get() = CommonUtils.isOnline(context)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        "Fragment Name: $TAG".showAsLogE()
        binding = inflate.invoke(inflater, container, false)
        onFragmentCreated()
        subscribe()
        subscribeDialogs()
        subscribeErrors()
        subscribeActions()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSharedViewEnabled) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onStart() {
        super.onStart()
        hideKeyboard()
        changeFragmentBackground(backgroundResId)
    }

    open fun subscribe() {
        "Not have a subscription".showAsLog()
    }

    open fun reloadActivity() {
        baseActivity?.reloadActivity()
    }

    private fun subscribeDialogs() {
        viewModel.dialogDetection.observeThis { params ->
            params.second.showAsDialog(params.first, null, {
                params.third?.let { it() }
            })
        }
    }

    private fun subscribeErrors() {
        viewModel.networkErrorDetection.observeThis {
            it.showAsErrorDialog()
        }
    }

    private fun subscribeActions() {
        viewModel.reloadAppDetection.observeThis {
            baseActivity?.reloadActivity()
        }
        /*
        viewModel.navigateFragmentDetection.observeThis {
            baseActivity?.navigateFragment(it)
        }
         */
    }

    /**
     *  XML icerisindeki degiskenin tanimlanmasi
     */
    open fun initBindingVariables() {
        "Not have a binding variable".showAsLog()
    }

    fun <T> LiveData<T>.observeThis(function: (T) -> Unit) {
        observe(viewLifecycleOwner) {
            it?.let {
                function(it)
            }
        }
    }

    private fun changeFragmentBackground(resId: Int, isColor: Boolean = false) {
        baseActivity?.changeBackground(resId, isColor)
    }

    fun setFullScreen(isFullScreen: Boolean) {
        val flags = if (isFullScreen) {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        } else {
            View.SYSTEM_UI_FLAG_VISIBLE
        }
        activity?.window?.decorView?.systemUiVisibility = flags
    }

    fun <T : ListenableWorker> executeWorkerWhenInternetConnected(java: Class<T>) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = OneTimeWorkRequest.Builder(java)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(requireContext()).enqueue(workRequest)
    }

    fun requestPermission(permissions: Array<String>) {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, 101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //granted
            onPermissionGranted(permissions)
        } else {
            //not granted
            onPermissionDenied()
        }
    }

    /**
     *  Sadece toast basar, Override edin
     */
    open fun onPermissionGranted(permissions: Array<String>) {
//        R.string.uygulamaya_izin_verdiniz.showAsToast()
    }

    /**
     *  Sadece dialog basar, Override edin
     */
    open fun onPermissionDenied() {
        R.string.bu_islemi_tamamlamak_icin_izin_vermeniz_gerekmektedir.showAsDialog(R.string.izinler)
    }


}