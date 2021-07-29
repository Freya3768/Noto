package com.noto.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.noto.app.databinding.ConfirmationDialogFragmentBinding
import com.noto.app.util.withBinding
import java.io.Serializable

class ConfirmationDialogFragment : BaseDialogFragment() {

    private val args by navArgs<ConfirmationDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ConfirmationDialogFragmentBinding.inflate(inflater, container, false).withBinding {

        tvTitle.text = args.title
        btnConfirm.text = args.btnText
        btnConfirm.setOnClickListener {
            dismiss()
            args.clickListener.onClick()
            findNavController().navigateUp()
        }
        if (args.description.isNullOrBlank())
            tvDescription.visibility = View.GONE
        else
            tvDescription.text = args.description
    }

    fun interface ConfirmationDialogClickListener : Serializable {
        fun onClick()
    }
}