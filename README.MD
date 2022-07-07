## 这是一个扩展AndroidX的lib
[![](https://jitpack.io/v/SheTieJun/AndroidX-KTX.svg)](https://jitpack.io/#SheTieJun/AndroidX-KTX)

## 调用使用

```
implementation 'com.github.SheTieJun:AndroidX-KTX:Tag'
```

1.选择联系人

```kotlin
pickContact {
    if (it != null) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
    }
}
```

2.选择多个文件，比如多个图片

```kotlin
selectMultipleFile {
    if (it != null) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
    }
}
```

3.拍摄视频

 ```kotlin
takeVideo {
    if (it != null) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
    }
}
```

4.申请权限

```kotlin
startRequestPermission(permission = READ_EXTERNAL_STORAGE) {
    if (it != null) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
    }
}

```

5.选择文件
```kotlin
selectFile {
    if (it != null) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
    }
}
```

6.拍摄图片

```kotlin
takePicture {
    if (it != null) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
    }
}
```

7. 其他... 
 
   
