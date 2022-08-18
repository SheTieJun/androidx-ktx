package me.shetj.activity

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope


/**
 * 获取上下文的 lifecycleScope
 *
 * - Context:
 *   - 1.Service
 *   - 2.Application
 *   - 3.Activity
 *
 * 这里需要判断context 是不是  AppCompatActivity
 *
 * 如果不是，我们需要去循环查找找
 */
val Context.lifeScope: CoroutineScope?
    get() {
        if (this is AppCompatActivity) {
            return this.lifecycleScope
        }
        var context = this
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context.lifecycleScope
            }
            context = context.baseContext
        }
        return null
    }


/**
 * 通过资源名字获取资源id
 */
fun Context.getIdByName(className: String, resName: String): Int {
    val packageName = packageName
    return applicationContext.resources.getIdentifier(resName, className, packageName)
}


/**
 * 通过包名打开Activity
 */
fun Context.openActivityByPackageName(packageName: String) {
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    startActivity(intent)
}

/**
 *  - 让APP到前台，前提是APP已经后台了
 *  - 如果代码无效，可能是因为APP被判定在前台
 */
fun Context.moveToFrontApp(){
    (getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)?.moveToFrontApp(packageName)
}


/**
 * - 通过包名，让APP到前台，前提是APP在后台了
 * - 如果代码无效，可能是因为APP被判定在前台
 */
fun ActivityManager.moveToFrontApp(packageName: String) {
    this.appTasks?.first {
        it.taskInfo.baseIntent.component?.packageName == packageName
    }?.apply {
        moveToFront()
    }
}

/**
 * - ABCD => B = ACDB
 * - ABCBD => B = ABCDB
 * 只把上一界面提前第一个
 */
fun Context.moveToFront(activity: Activity) {
    val intent: Intent = Intent(this, activity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
    }
    startActivity(intent)
}
