@file:JvmName("ImageUtils")
package com.ds.magicpicker

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

/**
 * Created by deepaksingh on 25/05/19.
 */
var CONST_IMAGE_URI = "image_uri"
var CONST_OPTION_CAMERA = "open_camera"
var CONST_OPTION_GALLERY = "open_gallery"
val CONST_PICK_IMAGE = 103

fun showImagePicker(context: Context, resultCode: Int) {
    val items = arrayOf<CharSequence>(context.getString(R.string.camera), context.getString(R.string.gallery))
    val builder = AlertDialog.Builder(context)
    builder.setTitle(context.getString(R.string.add_photo))
    builder.setItems(items) { dialog, item ->
        if (items[item] == context.getString(R.string.camera)) {
            (context as AppCompatActivity).startActivityForResult(PickerBuilder(context).openCamera().build(), resultCode)
        } else if (items[item] == context.getString(R.string.gallery)) {
            (context as AppCompatActivity).startActivityForResult(PickerBuilder(context).openGallery().build(), resultCode)
        }
    }
    builder.show()
}