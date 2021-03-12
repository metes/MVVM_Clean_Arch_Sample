package com.example.presenter.ui.custom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.presenter.R
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject


@InstallIn(ActivityComponent::class)
@Module
abstract class DialogModule {

    @Binds
    abstract fun bindGeneralDialog(impl: GeneralDialog): IGeneralDialog
}


interface IGeneralDialog {
    fun showDialog(fragmentManager: FragmentManager)
    var titleText: String
    var messageText: String?
    var messageTextSpanned: Spanned?
    var functionOk: (() -> Unit)?
    var functionCancel: (() -> Unit)?
    var functionNeutral: (() -> Unit)?
}

class GeneralDialog @Inject constructor(): DialogFragment(), IGeneralDialog {

    private var txtTitle: TextView? = null
    private var txtMessage: TextView? = null
    private var txtOk: TextView? = null
    private var txtCancel: TextView? = null
    private var txtNeutral: TextView? = null

    override var titleText: String = ""
    override var messageText: String? = ""
    override var messageTextSpanned: Spanned? = null
    override var functionOk: (() -> Unit)? = null
    override var functionCancel: (() -> Unit)? = null
    override var functionNeutral: (() -> Unit)? = null

    private fun getInstance(): GeneralDialog {
        return this
    }

    /**
     * Ust Use gosterilen dialoglar icin
     */
    private fun newInstance(): GeneralDialog {
        val newInstance = GeneralDialog()
        newInstance.titleText = titleText
        newInstance.messageText = messageText
        newInstance.messageTextSpanned = messageTextSpanned
        newInstance.functionOk = functionOk
        newInstance.functionCancel = functionCancel
        newInstance.functionNeutral = functionNeutral

        return newInstance
    }

    override fun showDialog(fragmentManager: FragmentManager) {
        val instance = if (isAdded) newInstance() else getInstance()
        instance.show(fragmentManager, "")
    }

    private fun clearParams() {
        titleText = ""
        messageText = ""
        messageTextSpanned = null
        functionOk = null
        functionCancel = null
        functionNeutral = null
        setButtonTexts()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        clearParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val root = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_general, null)
        initViews(root)

        setTextViews()
        setViewVisibilities()
        setClickListeners()
        setButtonTexts()

        return root
    }

    private fun initViews(root: View) {
        txtTitle = root.findViewById(R.id.txtTitle)
        txtMessage = root.findViewById(R.id.txtContent)
        txtOk = root.findViewById(R.id.txtOk)
        txtCancel = root.findViewById(R.id.txtCancel)
        txtNeutral = root.findViewById(R.id.txtNeutral)
    }

    private fun setTextViews() {
        txtTitle?.text = titleText
        txtMessage?.text = messageText ?: messageTextSpanned
    }

    private fun setViewVisibilities() {
//        txtTitle?.visibleIf(titleText.isNotBlank())
//        txtCancel?.visibleIf(functionOk != null)
    }

    private fun setClickListeners() {
        // her turlu "Tamam" butonu dialog'u kapatir, varsa functionOk'i calistirir.
        txtOk?.setOnClickListener {
            functionOk?.let { it() }
            dismiss()
            clearParams()
        }
        // functionOk varsa txtCancel gorunur olur (setViewVisibilities). txtCancel tiklandiginda;  butonu dialog'u kapatir, varsa functionCancel'i calistirir.
        functionOk?.let {
            txtCancel?.setOnClickListener {
                functionCancel?.let { it() }
                dismiss()
                clearParams()
            }
        }
        // functionNeutral varsa txtNeutral gorunur olur. functionCancel varsa; txtNeutral tiklandiginda;  butonu dialog'u kapatir, varsa functionCancel'i calistirir.
        functionNeutral?.let { functionNeutral ->
//            txtNeutral?.visibleIf(true)
            functionCancel?.let {
                txtNeutral?.setOnClickListener {
                    functionNeutral()
                    dismiss()
                    clearParams()
                }
            }?: clearParams()
        }
    }

    /**
     *  Tamam ve iptal buttonlarinin isimlerini gunceller
     */
    fun setButtonTexts(okText: String? = null, cancelText: String? = null) {
        txtOk?.text = okText ?: "tamam)"
        txtCancel?.text = cancelText ?: "iptal)"
    }
}
