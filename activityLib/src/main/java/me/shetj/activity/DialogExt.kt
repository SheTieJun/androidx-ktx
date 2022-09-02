package me.shetj.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding


/**
 *  Create sim dialog
 * ```
 *   createSimDialog<LayoutDialogTestBinding> (onViewCreated = {
 *      //viewBinding
 *   },
 *   setWindowSizeChange = {dialog,window ->
 *      //可以修改win大小
 *      window?.setLayout(900, LinearLayout.LayoutParams.WRAP_CONTENT)
 *   })
 * ```
 * @param VB viewBinding
 * @param onViewCreated
 * @param setWindowSizeChange
 */
inline fun <reified VB : ViewBinding> Context.createSimDialog(
    crossinline onViewCreated: ((mVB: VB) -> Unit) = { },
    crossinline setWindowSizeChange: ((dialog: AlertDialog, window: Window?) -> Unit) = { _, window ->
        window?.setLayout(900, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
): AlertDialog? {
    val mVB = VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, LayoutInflater.from(this)) as VB
    onViewCreated.invoke(mVB)
    return AlertDialog.Builder(this)
        .setView(mVB.root)
        .show()?.apply {
            setWindowSizeChange.invoke(this, this.window)
        }
}
