# ActivityResultLauncher说明

1. 使用`registerForActivityResult`注册得到`ActivityResultLauncher`，在`Activity`必须在`onStart`及之前执行，在`fragment`必须在`onCreate`
   及以前执行。
2. `ActivityResultLauncher`的注册实际上是通过`ActivityResultRegistry`注册获取的
3. `ActivityResultRegistry`有2个注册方法，其中一个并不需要`LifecycleOwner`，所以通过这个新的注册方法我们可以不遵守：在`Activity`必须在`onStart`
   及之前执行，在`fragment`必须在`onCreate`及以前执行。
4. `Fragment`在使用register的是时候需要注意Activity是不是为空，否则会异常

## 1.了解ActivityResultContract

首先我们需要了解有哪些自带的[ActivityResultContract](https://developer.android.com/reference/androidx/activity/result/contract/ActivityResultContract)

- StartActivityForResult: 通用的Contract,不做任何转换，Intent作为输入，ActivityResult作为输出，这也是最常用的一个协定。
- RequestMultiplePermissions： 用于请求一组权限
- RequestPermission: 用于请求单个权限
- TakePicturePreview: 调用MediaStore.ACTION_IMAGE_CAPTURE拍照，返回值为Bitmap图片
- TakePicture: 调用MediaStore.ACTION_IMAGE_CAPTURE拍照，并将图片保存到给定的Uri地址，返回true表示保存成功。
- TakeVideo: 调用MediaStore.ACTION_VIDEO_CAPTURE 拍摄视频，保存到给定的Uri地址，返回一张缩略图。
- PickContact: 从通讯录APP获取联系人
- GetContent: 提示用选择一条内容，返回一个通过ContentResolver#openInputStream(Uri)访问原生数据的Uri地址（content://形式） 。默认情况下，它增加了
  Intent#CATEGORY_OPENABLE, 返回可以表示流的内容。
- CreateDocument: 提示用户选择一个文档，返回一个(file:/http:/content:)开头的Uri。
- OpenMultipleDocuments: 提示用户选择文档（可以选择多个），分别返回它们的Uri，以List的形式。
- OpenDocumentTree: 提示用户选择一个目录，并返回用户选择的作为一个Uri返回，应用程序可以完全管理返回目录中的文档。

## 调用使用

1.选择联系人

```kotlin
pickContact {
    if (it != null) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
    }
}
```

2.选择多个文件，比如图片

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

7.剪切图片

```kotlin
cropImage(CropImageResult(it, 16, 9)) {
    if (it != null) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
    }
}
```