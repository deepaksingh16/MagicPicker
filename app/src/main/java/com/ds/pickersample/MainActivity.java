package com.ds.pickersample;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ds.magicpicker.ImageContants;
import com.ds.magicpicker.PickerBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGallery, btnCamera;
    ImageView imgTarget;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        btnGallery = (Button) findViewById(R.id.btn_gallery);
        btnCamera = (Button) findViewById(R.id.btn_camera);
        imgTarget = (ImageView) findViewById(R.id.iv_target);

        btnGallery.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_gallery:
                intent = new PickerBuilder(this).openGallery(true).build();
                break;
            case R.id.btn_camera:
                intent = new PickerBuilder(this).openCamera(true).build();
                break;
            default:
                    intent = new PickerBuilder(this).build();
        }
        startActivityForResult(intent, ImageContants.PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImageContants.PICK_IMAGE:
                    Glide.with(this).load(data.getData()).into(imgTarget);
                    break;
            }
        }
    }
}
