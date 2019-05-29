package com.ds.magicpicker

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by deepaksingh on 24/05/19.
 */
val PERM_READ_EXTERNAL_STORAGE: Int = 121
val PERM_WRITE_EXTERNAL_STORAGE : Int = 122
val PERM_CAMERA : Int = 123

fun checkStorageReadPermission(context: Context): Boolean {
    val currentAPIVersion : Int = Build.VERSION.SDK_INT
    if (currentAPIVersion >= Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                var alertBuilder = android.support.v7.app.AlertDialog.Builder(context)
                alertBuilder.setCancelable(true)
                alertBuilder.setTitle(context.getString(R.string.permission_required))
                alertBuilder.setMessage(context.getString(R.string.gallery_permission_required))
                alertBuilder.setPositiveButton(android.R.string.yes) { p0, p1 ->
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERM_READ_EXTERNAL_STORAGE)
                }
                alertBuilder.create()
                alertBuilder.show()
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERM_READ_EXTERNAL_STORAGE)
            }
            return false
        } else {
            return checkStorageWritePermission(context)
        }
    }
    return true
}

fun checkStorageWritePermission(context: Context): Boolean {
    val currentAPIVersion : Int = Build.VERSION.SDK_INT
    if (currentAPIVersion >= Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                var alertBuilder = android.support.v7.app.AlertDialog.Builder(context)
                alertBuilder.setCancelable(true)
                alertBuilder.setTitle(context.getString(R.string.permission_required))
                alertBuilder.setMessage(context.getString(R.string.write_permission_required))
                alertBuilder.setPositiveButton(android.R.string.yes) { p0, p1 ->
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_WRITE_EXTERNAL_STORAGE)
                }
                alertBuilder.create()
                alertBuilder.show()
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_WRITE_EXTERNAL_STORAGE)
            }
            return false
        } else {
            return true
        }
    }
    return true
}

fun checkCameraPermission(context: Context): Boolean {
    val currentAPIVersion : Int = Build.VERSION.SDK_INT
    if (currentAPIVersion >= Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.CAMERA)) {
                var alertBuilder = android.support.v7.app.AlertDialog.Builder(context)
                alertBuilder.setCancelable(true)
                alertBuilder.setTitle(context.getString(R.string.permission_required))
                alertBuilder.setMessage(context.getString(R.string.camera_permission_required))
                alertBuilder.setPositiveButton(android.R.string.yes) { p0, p1 ->
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CAMERA), PERM_CAMERA)
                }
                alertBuilder.create()
                alertBuilder.show()
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CAMERA), PERM_CAMERA)
            }
            return false
        } else {
            return checkStorageWritePermission(context)
        }
    }
    return true
}

fun verifyPermission(grantResults: IntArray) : Boolean {
    return (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
}



