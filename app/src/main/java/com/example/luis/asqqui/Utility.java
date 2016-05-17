package com.example.luis.asqqui;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.luis.asqqui.data.DatabaseContract;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Luis-DELL on 3/31/2016.
 */
public class Utility {

    public static String getPartyNamebyId(Context context, int id){
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(DatabaseContract.PartyEntry.CONTENT_URI,
                new String[]{DatabaseContract.PartyEntry.COLUMN_NAME_ABREVIATE},
                DatabaseContract.PartyEntry._ID + " = " + id,
                null,
                null);
        String name = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(DatabaseContract.PartyEntry.COLUMN_NAME_ABREVIATE));
            }
            cursor.close();
        } else {
            name = "PMDB";
        }
        return name;
    }

    // convert from bitmap to byte array
    public static byte[] convertImageToBytes(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        } else {
            return null;
        }
    }

    public static Bitmap convertBytesToImage(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static Bitmap compressImage (Bitmap image, int rate) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, rate, out);
        image = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        if (imageWidth >= imageHeight && imageWidth > 600) {
            imageWidth = imageWidth / 2;
            imageHeight = imageHeight / 2;
        } else if (imageHeight > imageWidth && imageHeight > 600) {
            imageWidth = imageWidth / 2;
            imageHeight = imageHeight / 2;
        }
        image = Bitmap.createScaledBitmap(image, imageWidth, imageHeight, true);
        return image;
    }
}
