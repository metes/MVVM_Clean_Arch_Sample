package com.example.mvvmkotlintest.ui.custom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.mvvmkotlintest.R
import com.example.mvvmkotlintest.utils.CommonUtils.visibleIf

class GeneralDialog(
    private val titleText: String,
    private val messageText: String?,
    private val messageTextSpanned: Spanned?,
    private val functionOk: (() -> Unit)? = null,
    private val functionCancel: (() -> Unit)? = null,
    private val functionNeutral: (() -> Unit)? = null
) : DialogFragment() {

    var txtTitle: TextView? = null
    var txtMessage: TextView? = null
    var txtOk: TextView? = null
    var txtCancel: TextView? = null
    var txtNeutral: TextView? = null

    var txtOkText: String? = null
    var txtCancelText: String? = null

    fun newInstance(): GeneralDialog {
        return GeneralDialog(
            titleText,
            messageText,
            messageTextSpanned,
            functionOk,
            functionCancel,
            functionNeutral
        )
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val root = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_general, null)
        txtTitle = root.findViewById(R.id.txtTitle)
        txtMessage = root.findViewById(R.id.txtContent)
        txtOk = root.findViewById(R.id.txtOk)
        txtCancel = root.findViewById(R.id.txtCancel)
        txtNeutral = root.findViewById(R.id.txtNeutral)


        isCancelable = false
        txtTitle?.visibleIf(titleText.isNotBlank())
        txtTitle?.text = titleText
        txtMessage?.text = messageText ?: messageTextSpanned
        txtOk?.setOnClickListener {
            dismiss()
            functionOk?.let { it() }
        }
        functionOk?.let {
            txtCancel?.setOnClickListener {
                dismiss()
                functionCancel?.let { it() }
            }
        }
        functionNeutral?.let {
            txtNeutral?.visibleIf(true)
            functionCancel?.let {
                txtNeutral?.setOnClickListener {
                    dismiss()
                    functionNeutral?.let { it() }
                }
            }

        }
        txtCancel?.visibleIf(functionOk != null)
        updateButtonTexts()

        return root
    }


    fun setButtonTexts(
        okText: String? = null,
        cancelText: String? = null
    ) {
        txtOkText = okText
        txtCancelText = cancelText
        updateButtonTexts()
    }

    private fun updateButtonTexts() {
        txtOk?.text = txtOkText ?: getString(android.R.string.ok)
        txtCancel?.text = txtCancelText ?: getString(android.R.string.cancel)
    }
}