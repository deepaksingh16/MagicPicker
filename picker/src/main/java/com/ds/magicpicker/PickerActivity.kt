package com.ds.magicpicker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.NonNull
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*



/**
 * Created by deepaksingh on 24/05/19.
 */
class PickerActivity : AppCompatActivity() {
    var isCameraSelected: Boolean = false
    val OPTION_GALLERY  : Int = 11
    val OPTION_CAMERA : Int = 12
    var mImageUri : Uri? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (intent.hasExtra(CONST_OPTION_CAMERA)) {
            isCameraSelected = true
            if (checkCameraPermission(this)) {
                openCamera()
            }
        } else {
            isCameraSelected = false
            if (checkStorageReadPermission(this)) {
                openGallery()
            }
        }
    }

    /**
     * Method to open gallery
     */
    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select File"), OPTION_GALLERY)
    }

    /**
     * Method to open camera
     */
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        mImageUri = FileProvider.getUriForFile(
                this,
                this.applicationContext.packageName + ".provider", getOutputMediaFile()!!)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri)
        startActivityForResult(intent, OPTION_CAMERA)
    }

    private fun sendFileObject() {
        val intent = Intent()
        intent.data = mImageUri
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    /**
     * Method copy input stream data to output stream
     * @param in
     * @param out
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun copyStream(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while ((`in`.read(buffer)) != -1) {
            out.write(buffer, 0, `in`.read(buffer))
        }
    }

    /**
     * Method to get path for image to be captured
     * @return
     */
    private fun getOutputMediaFile(): File? {
        val mediaStorageDir = File(Environment.getExternalStorageDirectory(), "MagicPicker")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        return File(mediaStorageDir.path + File.separator +
                "IMG_" + timeStamp + ".jpg")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPTION_CAMERA -> if (null != mImageUri) {
                    sendFileObject()
                } else {
                    finish()
                }
                OPTION_GALLERY -> {
                    mImageUri = data.data
                    if (null != mImageUri) {
                        sendFileObject()
                    } else {
                        finish()
                    }
                }
            }
        } else {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            grantResults: IntArray) {
        if (grantResults.isNotEmpty() && verifyPermission(grantResults)) {
            when (requestCode) {
                PERM_READ_EXTERNAL_STORAGE -> if (checkStorageWritePermission(this)) {
                    openGallery()
                }
                PERM_CAMERA -> if (checkStorageWritePermission(this)) {
                    openCamera()
                }
                PERM_WRITE_EXTERNAL_STORAGE -> if (isCameraSelected) {
                    openCamera()
                } else {
                    openGallery()
                }
                else -> finish()
            }
        } else {
            finish()
        }
    }
}