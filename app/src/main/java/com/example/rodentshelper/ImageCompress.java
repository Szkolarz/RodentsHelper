package com.example.rodentshelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.exifinterface.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageCompress extends Thread {

    public Bitmap compressChosenImage (Context context, Uri selectedImageUri, Bitmap bitmap) {

        byte[] byteArray;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byteArray = stream.toByteArray();
        //if more than ~1mb then compress it in a while loop
        while (byteArray.length > 1000000) {
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.8), (int) (bitmap.getHeight() * 0.8), true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
            //System.out.println("a");
        }

        return rectifyImage(bitmap, context, selectedImageUri);

    }

    public static byte[] getByteArray (byte[] byteArray) {
        return byteArray;
    }


    public static Bitmap rectifyImage(Bitmap originalBitmap , Context context, Uri uri){

        try{

            InputStream input = context.getContentResolver().openInputStream(uri);
            ExifInterface ei;

            if (Build.VERSION.SDK_INT > 23)
                ei = new ExifInterface(input);
            else
                ei = new ExifInterface(uri.getPath());

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(originalBitmap, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(originalBitmap, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(originalBitmap, 270);
                default:
                    return originalBitmap;
            }
        }catch (Exception e){
            return originalBitmap;
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}
