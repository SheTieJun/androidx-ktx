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

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.os.FileUtils
import android.provider.DocumentsContract
import android.provider.MediaStore.Audio
import android.provider.MediaStore.Images.ImageColumns
import android.provider.MediaStore.Images.Media
import android.provider.MediaStore.MediaColumns
import android.provider.MediaStore.Video
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.MessageDigest

/**
 * 安卓Q 文件基础操作
 */
object FileQUtils {

    /**
     * Keep file Visit 保存文件长时间访问
     * @param uri
     */
    fun Context.keepFileVisit(uri: Uri) {
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        contentResolver.takePersistableUriPermission(uri, flag)
    }

    /**
     * 根据Uri获取文件绝对路径，解决Android4.4以上版本Uri转换 兼容Android 10
     *
     * @param context
     * @param uri
     * @param isTemp Android 10 是否是临时文件名称，如果不是，会生成：时间戳+文件名，否则直接获取文件的文件名
     */
    fun getFileAbsolutePath(context: Context?, uri: Uri?, isTemp: Boolean = true): String? {
        if (context == null || uri == null) {
            return null
        }
        if (VERSION.SDK_INT < VERSION_CODES.KITKAT) {
            return getRealFilePath(context, uri)
        }
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT && VERSION.SDK_INT < VERSION_CODES.Q
            && DocumentsContract.isDocumentUri(context, uri)
        ) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                if (id.startsWith("raw:")) {
                    return id.replaceFirst("raw:", "")
                }
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = Media.EXTERNAL_CONTENT_URI
                    }

                    "video" -> {
                        contentUri = Video.Media.EXTERNAL_CONTENT_URI
                    }

                    "audio" -> {
                        contentUri = Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }
                val selection = Media._ID + "=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } // MediaStore (and general)
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            return uriToFileApiQ(context, uri,isTemp)
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) {
                uri.lastPathSegment
            } else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    //此方法 只能用于4.4以下的版本
    private fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri) {
            return null
        }
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null) {
            data = uri.path
        } else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val projection = arrayOf(ImageColumns.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun getDataColumn(
        context: Context,
        uri: Uri?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = Media.DATA
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    /**
     * Android 10 以上适配 另一种写法
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("Range")
    private fun getFileFromContentUri(context: Context, uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val filePath: String
        val filePathColumn = arrayOf(MediaColumns.DATA, MediaColumns.DISPLAY_NAME)
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            uri, filePathColumn, null,
            null, null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            try {
                filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]))
                return filePath
            } catch (e: Exception) {
            } finally {
                cursor.close()
            }
        }
        return ""
    }

    /**
     * Android 10 以上适配
     * @param context
     * @param uri
     * @return
     * disPlayName 为文件名 replace("/", "_") 为了防止文件名中有/导致文件保存失败（兼容pickVisualMedia）
     */
    @RequiresApi(api = VERSION_CODES.Q)
    private fun uriToFileApiQ(context: Context, uri: Uri, filename10IsTemp: Boolean): String? {
        return if (uri.scheme == ContentResolver.SCHEME_FILE) {
            File(requireNotNull(uri.path)).path
        } else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            // 把文件保存到沙盒,算是临时文件
            val contentResolver = context.contentResolver
            val displayName = getFileName(context, uri, filename10IsTemp).replace("/", "_") // 修复复制文件时，文件名中包含/导致的文件复制失败
            val ios = contentResolver.openInputStream(uri)
            if (ios != null) {
                if (filename10IsTemp) {//临时文件，放在cache目录下
                    File("${context.cacheDir.absolutePath}/$displayName")
                        .apply {
                            val fos = FileOutputStream(this)
                            FileUtils.copy(ios, fos)
                            fos.close()
                            ios.close()
                        }.path
                } else {
                    File("${context.filesDir.absolutePath}/$displayName")
                        .apply {
                            val fos = FileOutputStream(this)
                            FileUtils.copy(ios, fos)
                            fos.close()
                            ios.close()
                        }.path
                }
            } else {
                null
            }
        } else {
            null
        }
    }

    private fun getFileName(context: Context, uri: Uri, filename10IsTemp: Boolean = true): String {
        var fileName: String? = null
        val contentResolver = context.contentResolver
        return kotlin.runCatching {
            if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor.use { cu ->
                    if (cu != null && cu.moveToFirst()) {
                        fileName = cu.getString(cu.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
            if (fileName == null) {
                fileName = uri.lastPathSegment
            }
            //不是临时文件，就生成时间戳+文件名的名字，防止文件名重复被覆盖
            if (!filename10IsTemp && fileName != null) {
                fileName = System.currentTimeMillis().toString() + fileName
            }
            fileName!!
        }.getOrDefault("${uri.toString().md5()}.${
            MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(contentResolver.getType(uri))
        }")
    }


    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}