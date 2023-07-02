package com.dev.daggerapp.core.util.function

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dev.daggerapp.R
import com.dev.daggerapp.databinding.DialogCommonProgressBinding

object DialogUtils {
    fun showCommonProgressDialog(context: Context, dialog: Dialog) {
        val dialogBinding: DialogCommonProgressBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_common_progress, null, false
        )
        dialog.apply {
            setContentView(dialogBinding.root)
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }.show()
    }
    fun dismissDialog(dialog: Dialog) {
        dialog.dismiss()
    }
}