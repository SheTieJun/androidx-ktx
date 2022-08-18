```kotlin
/**
 * 获取上下文的 lifecycleScope
 *
 * - Context:
 * - 1.Service :null
 * - 2.Application :null
 * - 3.Activity :lifecycleScope
 */
val Context.lifeScope: CoroutineScope?
```

```kotlin
/**
 *  - 让APP到前台，前提是APP已经后台了
 *  - 如果代码无效，可能是因为APP被判定在前台
 */
fun Context.moveToFrontApp()

```

```kotlin
/**
 * - 通过包名，让APP到前台，前提是APP在后台了
 * - 如果代码无效，可能是因为APP被判定在前台
 */
fun ActivityManager.moveToFrontApp(packageName: String)  
```

```kotlin
/**
 * - ABCD => B = ACDB
 * - ABCBD => B = ABCDB
 * 只把上一界面提前第一个
 */
fun Context.moveToFront(activity: Activity)
```

```kotlin
/**
 * 通过资源名字获取资源id
 */
fun Context.getIdByName(className: String, resName: String) 
```

```kotlin
/**
 * 通过包名打开Activity
 */
fun Context.openActivityByPackageName(packageName: String)  
```
