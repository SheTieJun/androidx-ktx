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
- ~~基于'androidx.core:core-ktx:1.7.0'~~ 
- 基于'androidx.core:core-ktx:1.8.0'

## 2. WindowInsetsControllerCompat封装调用

[WindowInsetsControllerCompat使用](https://github.com/SheTieJun/BaseKit/wiki/WindowInsetsControllerCompat%E4%BD%BF%E7%94%A8)
- ~~基于'androidx.core:core-ktx:1.7.0'~~
- 基于'androidx.core:core-ktx:1.8.0'

## 3. LiveData扩展
[LiveData扩展](liveDataLib/README.MD)


## 4. Activity扩展
[ActivityExt使用](activityLib/ActivityExt.MD) 

## 5. SoftKeyboardExt键盘相关
[SoftKeyboardExt.kt](activityLib/src/main/java/me/shetj/activity/SoftKeyboardExt.kt)： 键盘隐藏显示/是否可见/高度变化

## 6. DataStoreKit代替SP保存和获取数据的封装使用
[DataStoreKit.kt](dataStoreLib/src/main/java/me/shetj/datastore/DataStoreKit.kt): 代替SP保存和获取数据


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
+ 添加[DialogKt](activityLib/src/main/java/me/shetj/activity/DialogExt.kt)，快速出击dialog

### 0.0.5
+ 升级到'androidx.core:core-ktx:1.8.0'
+ 添加[SoftKeyboardExt.kt](activityLib/src/main/java/me/shetj/activity/SoftKeyboardExt.kt)：键盘隐藏显示/是否可见/高度变化
+ 新增`datastore-preferences`工具类封装[DataStoreKit.kt](dataStoreLib/src/main/java/me/shetj/datastore/DataStoreKit.kt): DataStore代替SP保存和获取数据