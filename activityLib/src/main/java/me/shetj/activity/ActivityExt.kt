/*
 * MIT License
 *
 * Copyright (c) 2019 SheTieJun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.shetj.activity

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


/**
 * @param isFinishOnTouchOutside 是否点击window 关闭activity
 */
@JvmOverloads
fun AppCompatActivity.cleanBackground(isFinishOnTouchOutside: Boolean = true) {
    val mWindow = window
    mWindow.setBackgroundDrawable(null)
    mWindow.setGravity(Gravity.CENTER)
    setFinishOnTouchOutside(isFinishOnTouchOutside)
}

/**
 * 保持常亮
 */
fun AppCompatActivity.addKeepScreenOn() {
    window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

/**
 * 去除常亮
 */
fun AppCompatActivity.clearKeepScreenOn() {
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





