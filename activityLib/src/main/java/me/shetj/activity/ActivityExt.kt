package me.shetj.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker


/**
 * @param isFinishOnTouchOutside 是否点击window 关闭activity
 */
@JvmOverloads
fun ComponentActivity.cleanBackground(isFinishOnTouchOutside: Boolean = true) {
    window?.setBackgroundDrawable(null)
    window?.setGravity(Gravity.CENTER)
    setFinishOnTouchOutside(isFinishOnTouchOutside)
}

/**
 * 保持常亮
 */
fun ComponentActivity.addKeepScreenOn() {
    window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

/**
 * 去除常亮
 */
fun ComponentActivity.clearKeepScreenOn() {
    window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}


fun Activity.onBackGoHome() {
    try {
        val i = Intent(Intent.ACTION_MAIN)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        i.addCategory(Intent.CATEGORY_HOME)
        startActivity(i)
    } catch (e: Exception) {
        onBackPressed()
    }
}

fun Activity.hasPermission(
    vararg permissions: String,
    isRequest: Boolean = true
): Boolean {
    val permissionsCheck: MutableList<String> = ArrayList()
    for (permission in permissions) {
        if (PermissionChecker.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsCheck.add(permission)
        }
    }
    if (permissionsCheck.isEmpty()) return true
    if (isRequest) {
        ActivityCompat.requestPermissions(this, permissionsCheck.toTypedArray(), 100)
    }
    return false
}




