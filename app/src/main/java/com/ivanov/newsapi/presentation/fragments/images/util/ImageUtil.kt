package com.ivanov.newsapi.presentation.fragments.images.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.support.media.ExifInterface
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

private const val MAX_HEIGHT = 1024
private const val MAX_WIDTH = 1024

object ImageUtil {

    @SuppressLint("SimpleDateFormat")
    fun createFile(context: Context): File? {
        val timestamp = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date())
        val dir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("image_${timestamp}", ".jpg", dir).apply {
            createNewFile()
            deleteOnExit()
        }
    }

    fun decodeSampledBitmapFromResource(context: Context, imageUri: Uri): Bitmap? {
        var input: InputStream? = context.contentResolver.openInputStream(imageUri)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, options)
        input?.close()

        options.inSampleSize = calculateInSampleSize(options)

        options.inJustDecodeBounds = false
        input = context.contentResolver.openInputStream(imageUri)
        var bitmap = BitmapFactory.decodeStream(input, null, options)
        bitmap = rotateImage(context, bitmap!!, imageUri)

        return bitmap
    }

    private fun rotateImage(context: Context, bitmap: Bitmap, imageUri: Uri): Bitmap? {
        val input: InputStream? = context.contentResolver.openInputStream(imageUri)
        val exifInterface = ExifInterface(input!!)

        val orientation: Int = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotate(bitmap, 90)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotate(bitmap, 180)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotate(bitmap, 270)
            }
            else -> bitmap
        }
    }

    private fun rotate(bitmap: Bitmap, degree: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val bmRotate = Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
        bitmap.recycle()
        return bmRotate
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > MAX_HEIGHT || width > MAX_WIDTH) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= MAX_HEIGHT
                && halfWidth / inSampleSize >= MAX_WIDTH
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}