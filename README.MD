## 这是一个扩展AndroidX的lib

[![](https://jitpack.io/v/SheTieJun/androidx-ktx.svg)](https://jitpack.io/#SheTieJun/androidx-ktx)

```
implementation 'com.github.SheTieJun:androidx-ktx:Tag'
```

或者单独获取

```kotlin
implementation 'com.github.SheTieJun.androidx-ktx:activityLib:Tag'// tag >=0.0.1 ,min 32 ,因为jetpack
implementation 'com.github.SheTieJun.androidx-ktx:livedatelib:Tag' // tag >=0.0.3
implementation 'com.github.SheTieJun.androidx-ktx:dataStoreLib:Tag' // tag >=0.0.7
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

[SoftKeyboardExt使用说明](activityLib/SoftKeyboardExt.MD)： 键盘隐藏显示/是否可见/高度变化

## 6. DataStoreKit代替SP保存和获取数据的封装使用

[DataStoreKit使用说明](dataStoreLib): 代替SP保存和获取数据

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
+ 新增`datastore-preferences`工具类封装[DataStoreKit.kt](dataStoreLib/src/main/java/me/shetj/datastore/DataStoreKit.kt):
  DataStore代替SP保存和获取数据
+ 这之后的版本`minCompileSdk=32` 了

### 0.0.8

+ `dataStoreLib`新增`getFirstBlock` &`saveBlock` &`clearBlcok`【非常规调用】
+ Fix `FileQUtils`低版本获取路径错误的问题
+ update `setAppearance` :只修改状态栏，不在修改导航栏的颜色

### 待定
+ - 基于'androidx.core:core-ktx:1.9.0' ,`minCompileSdk=33`
- `activityLib`新增`pickVisualMedia`&`pickMultipleVisualMedia`选择媒体文件
- update：`WinCompatExt.kt`:`immerse`: 支持只沉浸状态栏或者只沉浸导航栏 
- Fix:`getFileAbsolutePath` :可能获取到`raw：`开头的路径的文件导致的崩溃问题