package com.ds.magicpicker;

import android.content.Context;
import android.content.Intent;

/**
 * Created by deepak on 14/3/18.
 */

public class PickerBuilder {

    private Context mContext;
    private boolean isCamera;

    public PickerBuilder(Context context) {
        mContext = context;
    }

    public PickerBuilder openCamera(boolean open) {
        isCamera = open;
        return this;
    }

    public PickerBuilder openGallery(boolean open) {
        isCamera = !open;
        return this;
    }

    public Intent build() {
        Intent intent = new Intent(mContext, PickerActivity.class);
        if (isCamera) {
            intent.putExtra(ImageContants.OPTION_CAMERA, true);
        } else {
            intent.putExtra(ImageContants.OPTION_GALLERY, true);
        }
        return intent;
    }
}
