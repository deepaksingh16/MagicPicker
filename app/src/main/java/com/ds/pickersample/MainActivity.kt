package com.ds.pickersample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ds.magicpicker.CONST_PICK_IMAGE
import com.ds.magicpicker.PickerBuilder

/**
 * Created by deepaksingh on 25/05/19.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    internal lateinit var btnGallery: Button
    internal lateinit var btnCamera: Button
    internal lateinit var imgTarget: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        btnGallery = findViewById<View>(R.id.btn_gallery) as Button
        btnCamera = findViewById<View>(R.id.btn_camera) as Button
        imgTarget = findViewById<View>(R.id.iv_target) as ImageView

        btnGallery.setOnClickListener(this)
        btnCamera.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent: Intent
        when (view.id) {
            R.id.btn_gallery -> intent = PickerBuilder(this).openGallery().build()
            R.id.btn_camera -> intent = PickerBuilder(this).openCamera().build()
            else -> intent = PickerBuilder(this).build()
        }
        startActivityForResult(intent, CONST_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CONST_PICK_IMAGE -> Glide.with(this).load(data?.data).into(imgTarget)
            }
        }
    }
}
