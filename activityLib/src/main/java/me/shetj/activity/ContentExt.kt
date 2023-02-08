package me.shetj.activity

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date


/**
 * Create image path uri
 * 兼容核心就是这里
 * @param context
 * @return 图片的uri
 */
fun createImagePathUri(context: Context): Uri {
    return when {
        Build.VERSION.SDK_INT >= VERSION_CODES.Q -> {
            createImageUri(context)
        }
        Build.VERSION.SDK_INT >= VERSION_CODES.N -> {
            val file = File(createImagePath(context))
            FileProvider.getUriForFile(
                context.applicationContext,
                context.packageName + ".FileProvider",
                file
            )
        }
        else -> {
            val file = File(createImagePath(context))
            Uri.fromFile(file)
        }
    }
}

internal fun createImagePath(context: Context): String {
    val timeFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
    val time = System.currentTimeMillis()
    val imageName = timeFormatter.format(Date(time))
    return getPath(
        root = context.getFilesDir(Environment.DIRECTORY_PICTURES),
        packagePath = "image"
    ) + "/" + imageName + ".jpg"
}

internal fun createImageUri(context: Context): Uri {
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        ContentValues()
    ) ?: throw NullPointerException("create createImageUri fail")
}

/**
 * Create video path uri
 *
 * @param context
 * @return 图片的uri
 */
fun createVideoPathUri(context: Context): Uri {
    return when {
        Build.VERSION.SDK_INT >= VERSION_CODES.Q -> {
            createVideoUri(context)
        }
        Build.VERSION.SDK_INT >= VERSION_CODES.N -> {
            val file = File(createVideoPath(context))
            FileProvider.getUriForFile(
                context.applicationContext,
                context.packageName + ".FileProvider",
                file
            )
        }
        else -> {
            val file = File(createVideoPath(context))
            Uri.fromFile(file)
        }
    }
}


@RequiresApi(VERSION_CODES.Q)
internal fun createVideoUri(context: Context): Uri {
    return context.contentResolver.insert(
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        ContentValues().apply {
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        }
    ) ?: throw NullPointerException("create createImageUri fail")
}

internal fun getPath(root: String, packagePath: String): String {
    val path = StringBuilder(root)
    val f = packagePath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    for (aF in f) {
        val dirFile = File("$path/$aF")
        if (!dirFile.exists()) {
            dirFile.mkdir()
        }
        path.append("/").append(aF)
    }
    return path.toString()
}


internal fun createVideoPath(context: Context): String {
    val timeFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
    val time = System.currentTimeMillis()
    val imageName = timeFormatter.format(Date(time))
    return getPath(
        root = context.getFilesDir(Environment.DIRECTORY_PICTURES),
        packagePath = "video"
    ) + "/" + imageName + ".mp4"
}


/**
 * mnt/sdcard/Android/data/< package name >/files/type
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
 *   type =   {{@link android.os.EnvironmentDIRECTORY_MUSIC}},
 *            {@link android.os.Environment#DIRECTORY_PODCASTS},
 *            {@link android.os.Environment#DIRECTORY_RINGTONES},
 *            {@link android.os.Environment#DIRECTORY_ALARMS},
 *            {@link android.os.Environment#DIRECTORY_NOTIFICATIONS},
 *            {@link android.os.Environment#DIRECTORY_PICTURES}, or
 *            {@link android.os.Environment#DIRECTORY_MOVIES}. or null
 */
internal fun Context.getFilesDir(type: String = Environment.DIRECTORY_DOWNLOADS): String {
    val file: File? = getExternalFilesDir(type)
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && file != null) {
        file.absolutePath
    } else {
        filesDir.toString() + File.separator + type
    }
}

/**
 * 删除文件
 */
fun Context.delFile(uri: Uri) {
    DocumentsContract.deleteDocument(contentResolver, uri)
}