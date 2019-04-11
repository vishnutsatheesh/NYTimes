package com.nytimes.util

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.ProgressBar
import com.nytimes.R

/**
 * Created by Vishnu
 */

class ProgressDialog(internal var mContext: Context) : Dialog(mContext) {
    internal var dialog1: Dialog? = null

    fun showDialog(): Dialog {
        dialog1 = ProgressDialog(mContext)
        val progressBar = ProgressBar(mContext)
        dialog1!!.addContentView(progressBar, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        if (progressBar.indeterminateDrawable != null) {
            progressBar.indeterminateDrawable.setColorFilter(mContext.resources.getColor(R.color
                    .colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN)
        }
        try {
            dialog1!!.show()
        } catch (e: Exception) {
            Log.e("Exception", "" + e.message)
        }

        return dialog1 as ProgressDialog
    }

    fun dismissDialog() {
        if (dialog1 != null && dialog1!!.isShowing) {
            dialog1!!.dismiss()
        }
    }
}
