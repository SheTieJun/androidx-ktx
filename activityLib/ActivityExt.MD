# Activity扩展函数

```kotlin
/**
* 通过包名，让APP到前台，前提是APP在后台了，如果代码无效，可能是因为APP被判定在前台
*/
fun ActivityManager.moveToFrontApp(packageName: String)  
/**
 * ABCD => B = ACDB
 * ABCBD => B = ABCDB
 * 只把上一界面提前第一个
 */
fun Context.moveToFront(activity: Activity)  

/**
 * @param isFinishOnTouchOutside 是否点击window 关闭activity
 */
@JvmOverloads
fun AppCompatActivity.cleanBackground(isFinishOnTouchOutside: Boolean = true) 

/**
 * 保持常亮,onResume
 */
fun AppCompatActivity.addKeepScreenOn()  

/**
 * 去除常亮,onStop
 */
fun AppCompatActivity.clearKeepScreenOn()


/**
 * 去home
 */
fun Activity.onBackGoHome()  
```