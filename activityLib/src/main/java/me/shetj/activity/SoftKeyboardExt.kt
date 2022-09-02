package me.shetj.activity

import android.app.Activity
import android.os.Build
import android.os.Build.VERSION
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type

/**
 * 隐藏键盘
 */
fun Activity.hideSoftKeyboard() {
    windowInsetsController.hide(Type.ime())
}


/**
 * Show soft keyboard 显示软键盘
 *
 */
fun Activity.showSoftKeyboard() {
    windowInsetsController.show(Type.ime())
}


/**
 * Is visible key board
 * 判断键盘是否见
 * @return boolean 是否见
 */
fun Activity.isVisibleKeyBoard(): Boolean {
    return windowInsets?.isVisible(Type.ime()) ?: false
}


/**
 * Add key bord height change call back
 * 键盘高度监听
 * @param view
 * @param onAction
 * @receiver
 */
fun addKeyBordHeightChangeCallBack(view: View, onAction: (height: Int) -> Unit) {
    var posBottom: Int
    if (VERSION.SDK_INT >=Build.VERSION_CODES.R) {
        val cb = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
            override fun onProgress(
                insets: WindowInsets,
                animations: MutableList<WindowInsetsAnimation>
            ): WindowInsets {
                posBottom = insets.getInsets(Type.ime()).bottom +
                        insets.getInsets(Type.systemBars()).bottom
                onAction.invoke(posBottom)
                return insets
            }
        }
        view.setWindowInsetsAnimationCallback(cb)
    } else {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            posBottom = insets.getInsets(Type.ime()).bottom +
                    insets.getInsets(Type.systemBars()).bottom
            onAction.invoke(posBottom)
            insets
        }
    }
}