package com.example.bookcollectionapp.common

import android.graphics.Bitmap
import java.io.File

fun File.createFileAndDirs() = apply {
    parentFile?.mkdirs()
    createNewFile()
}

fun File.write(
    bitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 80
) = apply {
    createFileAndDirs()
    outputStream().use {
        bitmap.compress(format, quality, it)
        it.flush()
    }
}