package me.shetj.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding


inline fun <reified VB : ViewBinding> Context.createSimDialog(
    crossinline onBeforeShow: ((mVB: VB, dialog: AlertDialog) -> Unit) = { _, _ -> },
    crossinline onViewCreated: ((mVB: VB, dialog: AlertDialog) -> Unit) = { _, _ -> },
    crossinline setWindowSizeChange: ((dialog: AlertDialog, window: Window?) -> Unit) = { _, window ->
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setDimAmount(0.3f)
        window?.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
): AlertDialog {
    val mVB = VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, LayoutInflater.from(this)) as VB
    return AlertDialog.Builder(this)
        .setView(mVB.root).create().apply {
            onBeforeShow.invoke(mVB, this) //can  dialog.setOnShowListener
            this.show()
            onViewCreated.invoke(mVB, this)
            setWindowSizeChange.invoke(this, this.window)
        }
}