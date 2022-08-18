package me.shetj.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding


/**
 *  create Fast
 * ```
 *   createSimDialog<LayoutDialogTestBinding> (onViewCreated = {
 *
 *   },
 *   setWindowSizeChange = {dialog,window ->
 *
 *   })
 * ```
 */
inline fun <reified VB : ViewBinding> Context.createSimDialog(
    crossinline onViewCreated: ((mVB: VB) -> Unit) = { },
    crossinline setWindowSizeChange: ((dialog:AlertDialog,window:Window?) -> Unit) = { _, window ->
         window?.setLayout(900, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
): AlertDialog? {
    val mVB = VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, LayoutInflater.from(this)) as VB
    onViewCreated.invoke(mVB)
    return AlertDialog.Builder(this)
        .setView(mVB.root)
        .show()?.apply {
            setWindowSizeChange.invoke(this,this.window)
        }
}
