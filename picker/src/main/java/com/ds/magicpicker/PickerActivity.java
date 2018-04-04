package com.ds.magicpicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by deepak on 14/3/18.
 */

public class PickerActivity extends AppCompatActivity {

    private String optionSelected;
    private static final int OPTION_GALLERY = 11;
    private static final int OPTION_CAMERA = 12;
    private Uri mImageUri;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getIntent().hasExtra(ImageContants.OPTION_CAMERA)) {
            optionSelected = getString(R.string.camera);
            if (PermissionUtil.checkCameraPermission(PickerActivity.this)) {
                openCamera();
            }
        } else {
            optionSelected = getString(R.string.gallery);
            if(PermissionUtil.checkStoragePermission(PickerActivity.this)) {
                openGallery();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (PermissionUtil.verifyPermission(grantResults)) {
            switch (requestCode) {
                case PermissionUtil.PERM_READ_EXTERNAL_STORAGE:
                    if (PermissionUtil.checkStorageWritePermission(this)) {
                        openGallery();
                    }
                    break;
                case PermissionUtil.PERM_CAMERA:
                    if (PermissionUtil.checkStorageWritePermission(this)) {
                        openCamera();
                    }
                    break;
                case PermissionUtil.PERM_WRITE_EXTERNAL_STORAGE:
                    if (optionSelected.equalsIgnoreCase(getString(R.string.camera))) {
                        openCamera();
                    } else {
                        openGallery();
                    }
                    break;
                default:
                    finish();
                    break;
            }
        } else {
            finish();
        }
    }

    /**
     * Method to open gallery
     */
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"),OPTION_GALLERY);
    }

    /**
     * Method to open camera
     */
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mImageUri = FileProvider.getUriForFile(
                this,
                this.getApplicationContext().getPackageName() + ".provider", getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, OPTION_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case OPTION_CAMERA:
                    if (null != mImageUri) {
                        sendFileObject();
                    } else {
                        finish();
                    }
                    break;
                case OPTION_GALLERY:
                    mImageUri = data.getData();
                    if (null != mImageUri) {
                        sendFileObject();
                    } else {
                        finish();
                    }
                    break;
            }
        } else {
            finish();
        }
    }

    private void sendFileObject() {
        Intent intent = new Intent();
        intent.setData(mImageUri);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Method copy input stream data to output stream
     * @param in
     * @param out
     * @throws IOException
     */
    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /**
     * Method to get path for image to be captured
     * @return
     */
    private File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "MagicPicker");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
}
