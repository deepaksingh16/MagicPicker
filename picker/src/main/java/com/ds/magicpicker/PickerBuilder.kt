package com.ds.magicpicker

import android.content.Context
import android.content.Intent

/**
 * Created by deepaksingh on 25/05/19.
 */
class PickerBuilder(private val mContext: Context) {
    private var isCamera: Boolean = false

    fun openCamera(): PickerBuilder {
        isCamera = true
        return this
    }

    fun openGallery(): PickerBuilder {
        isCamera = false
        return this
    }

    fun build(): Intent {
        val intent = Intent(mContext, PickerActivity::class.java)
        if (isCamera) {
            intent.putExtra(CONST_OPTION_CAMERA, true)
        } else {
            intent.putExtra(CONST_OPTION_GALLERY, true)
        }
        return intent
    }
}