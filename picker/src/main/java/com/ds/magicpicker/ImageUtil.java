package com.ds.magicpicker;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by deepak on 14/3/18.
 */

public class ImageUtil {
    public static void showImagePicker(final Context context, final int resultCode) {
        final CharSequence[] items = {context.getString(R.string.camera)
                , context.getString(R.string.gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(context.getString(R.string.camera))) {
                    ((AppCompatActivity) context).startActivityForResult(new PickerBuilder(context).openCamera(true).build(), resultCode);
                } else if (items[item].equals(context.getString(R.string.gallery))) {
                    ((AppCompatActivity) context).startActivityForResult(new PickerBuilder(context).openGallery(true).build(), resultCode);
                }
            }
        });
        builder.show();
    }
}
