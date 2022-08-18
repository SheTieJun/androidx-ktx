## 这是一个扩展AndroidX的lib
[![](https://jitpack.io/v/SheTieJun/AndroidX-KTX.svg)](https://jitpack.io/#SheTieJun/AndroidX-KTX)


```
implementation 'com.github.SheTieJun:AndroidX-KTX:Tag'
```
或者单独获取
```kotlin
implementation 'com.github.SheTieJun.AndroidX-KTX:activityLib:Tag'
implementation 'com.github.SheTieJun.AndroidX-KTX:livedatelib:Tag'
```

## 1. ActivityResultLauncher封装使用

[ActivityResultLauncher使用](activityLib/ActivityResult.MD)

## 2. WindowInsetsControllerCompat封装调用

[WindowInsetsControllerCompat使用](https://github.com/SheTieJun/BaseKit/wiki/WindowInsetsControllerCompat%E4%BD%BF%E7%94%A8)


## 3. LiveData扩展
[LiveData扩展](liveDataLib/README.MD)


## 4. Activity扩展
[ActivityExt使用](activityLib/ActivityExt.MD) 



## UpdateLog

### 0.0.1
+ ActivityResultLauncher封装扩展

### 0.0.2
+ WindowInsetsControllerCompat封装扩展

### 0.0.3
+ LiveData扩展
+ ActivityExt扩展

### 0.0.4
+ [CropImageContract](activityLib/src/main/java/me/shetj/activity/CropImageContract.kt) 剪切图片
+ 添加[Context扩展](activityLib/src/main/java/me/shetj/activity/ContextExt.kt)
  + 主要添加`Context.lifeScope` 在`Dialog`等一些地方去获取`AppCompatActivity`的`lifecycleScope`
+ 添加[DialogKt](activityLib/src/main/java/me/shetj/activity/DialogKt.kt)，快速出击dialog