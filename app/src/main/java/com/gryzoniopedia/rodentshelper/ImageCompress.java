package com.gryzoniopedia.rodentshelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;

import androidx.exifinterface.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageCompress extends Thread {

    public Bitmap compressChosenImage (Context context, Uri selectedImageUri, Bitmap bitmap) {

        byte[] byteArray;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byteArray = stream.toByteArray();
        //if more than ~500kb then compress it in a while loop    (default: 1000000)
        while (byteArray.length > 500000) {
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.8), (int) (bitmap.getHeight() * 0.8), true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
        }

        return rectifyImage(bitmap, context, selectedImageUri);

    }


    //method to properly rotate the image
    //(there is a bug where image is bad rotated after converting - that's why this method is needed)
    private static Bitmap rectifyImage(Bitmap originalBitmap , Context context, Uri uri){
        try{
            InputStream input = context.getContentResolver().openInputStream(uri);
            ExifInterface ei;

            if (Build.VERSION.SDK_INT > 23)
                ei = new ExifInterface(input);
            else
                ei = new ExifInterface(uri.getPath());

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            input.close();
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
