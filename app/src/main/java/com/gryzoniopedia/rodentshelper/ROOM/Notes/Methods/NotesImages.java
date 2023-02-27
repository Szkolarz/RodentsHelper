package com.gryzoniopedia.rodentshelper.ROOM.Notes.Methods;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.Alerts;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.gryzoniopedia.rodentshelper.ImageCompress;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAONotes;
import com.gryzoniopedia.rodentshelper.ROOM.DateFormat;
import com.gryzoniopedia.rodentshelper.ROOM.Notes.NotesModel;
import com.gryzoniopedia.rodentshelper.ROOM.Notes.ViewNotes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class NotesImages {




    public void displayImage(AddEditNotes context, ImageView imageView1, ImageView imageView2, ImageView imageView3,
                             ImageView imageView4, Uri selectedImageUri, Integer intentCode,
                             Button buttonAdd_notes, Button buttonSaveEdit_notes,
                             TextView textViewDeleteImage1_notes, TextView textViewDeleteImage2_notes,
                             TextView textViewDeleteImage3_notes, TextView textViewDeleteImage4_notes,
                             View viewImages_notes, LinearLayout linearLayoutImage2_notes,
                             LinearLayout linearLayoutImage3_notes, LinearLayout linearLayoutImage4_notes) {

        Drawable res = context.getResources().getDrawable(R.drawable.loading);

        if (intentCode == 1)
            imageView1.setImageDrawable(res);
        else if (intentCode == 2)
            imageView2.setImageDrawable(res);
        else if (intentCode == 3)
            imageView3.setImageDrawable(res);
        else if (intentCode == 4)
            imageView4.setImageDrawable(res);


        buttonSaveEdit_notes.setEnabled(false);
        buttonAdd_notes.setEnabled(false);
        buttonAdd_notes.setBackgroundColor(Color.GRAY);
        buttonAdd_notes.setTextColor(Color.BLACK);
        buttonSaveEdit_notes.setTextColor(Color.BLACK);
        buttonSaveEdit_notes.setBackgroundColor(Color.GRAY);


        Thread thread = new Thread(() -> {

            Bitmap bitmapTemp;
            Bitmap bitmap = null;

            try {
                bitmapTemp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
                ImageCompress imageCompress = new ImageCompress();
                bitmap = imageCompress.compressChosenImage(context.getApplicationContext(), selectedImageUri, bitmapTemp);
            } catch (IOException e) {
                Log.e("NotesImages", Log.getStackTraceString(e));
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            } catch (NullPointerException e) {
                System.out.println("Bitmap error " + e);
            }

            if (intentCode == 1)
                AddEditNotes.byteArray1 = stream.toByteArray();
            else if (intentCode == 2)
                AddEditNotes.byteArray2 = stream.toByteArray();
            else if (intentCode == 3)
                AddEditNotes.byteArray3 = stream.toByteArray();
            else if (intentCode == 4)
                AddEditNotes.byteArray4 = stream.toByteArray();

            final Bitmap bitmapView = bitmap;

            context.runOnUiThread(() -> {

                buttonSaveEdit_notes.setEnabled(true);
                buttonAdd_notes.setEnabled(true);
                buttonAdd_notes.setBackgroundColor(Color.parseColor("#5397DF"));
                buttonSaveEdit_notes.setBackgroundColor(Color.parseColor("#5397DF"));
                buttonAdd_notes.setTextColor(Color.WHITE);
                buttonSaveEdit_notes.setTextColor(Color.WHITE);

                try {
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                setUpImageOrder(textViewDeleteImage1_notes, textViewDeleteImage2_notes, textViewDeleteImage3_notes,
                        textViewDeleteImage4_notes, viewImages_notes, linearLayoutImage2_notes, linearLayoutImage3_notes,
                        linearLayoutImage4_notes);

                if (intentCode == 1) {
                    imageView1.setImageBitmap(bitmapView);
                    checkImage(AddEditNotes.byteArray1, textViewDeleteImage1_notes);
                } else if (intentCode == 2) {
                    imageView2.setImageBitmap(bitmapView);
                    checkImage(AddEditNotes.byteArray2, textViewDeleteImage2_notes);
                } else if (intentCode == 3) {
                    imageView3.setImageBitmap(bitmapView);
                    checkImage(AddEditNotes.byteArray3, textViewDeleteImage3_notes);
                } else if (intentCode == 4) {
                    imageView4.setImageBitmap(bitmapView);
                    checkImage(AddEditNotes.byteArray4, textViewDeleteImage4_notes);
                }
            });

        });

        thread.start();
    }


    public void setUpImageOrder (TextView textViewDeleteImage1_notes, TextView textViewDeleteImage2_notes,
                                 TextView textViewDeleteImage3_notes, TextView textViewDeleteImage4_notes,
                                 View viewImages_notes, LinearLayout linearLayoutImage2_notes,
                                 LinearLayout linearLayoutImage3_notes, LinearLayout linearLayoutImage4_notes) {

        if (AddEditNotes.byteArray2 == null)
            linearLayoutImage2_notes.setVisibility(View.GONE);
        if (AddEditNotes.byteArray3 == null)
            linearLayoutImage3_notes.setVisibility(View.GONE);
        if (AddEditNotes.byteArray4 == null)
            linearLayoutImage4_notes.setVisibility(View.GONE);



        if (AddEditNotes.byteArray4 != null) {
            linearLayoutImage4_notes.setVisibility(View.VISIBLE);
            linearLayoutImage3_notes.setVisibility(View.VISIBLE);
            linearLayoutImage2_notes.setVisibility(View.VISIBLE);
            textViewDeleteImage4_notes.setVisibility(View.VISIBLE);
            viewImages_notes.setVisibility(View.VISIBLE);
        } else if (AddEditNotes.byteArray3 != null) {
            linearLayoutImage3_notes.setVisibility(View.VISIBLE);
            linearLayoutImage4_notes.setVisibility(View.VISIBLE);
            linearLayoutImage2_notes.setVisibility(View.VISIBLE);
            textViewDeleteImage3_notes.setVisibility(View.VISIBLE);
            viewImages_notes.setVisibility(View.VISIBLE);
        } else if (AddEditNotes.byteArray2 != null) {
            linearLayoutImage2_notes.setVisibility(View.VISIBLE);
            linearLayoutImage3_notes.setVisibility(View.VISIBLE);
            textViewDeleteImage2_notes.setVisibility(View.VISIBLE);
            viewImages_notes.setVisibility(View.VISIBLE);
            linearLayoutImage4_notes.setVisibility(View.GONE);
        } else if (AddEditNotes.byteArray1 != null) {
            textViewDeleteImage1_notes.setVisibility(View.VISIBLE);
            linearLayoutImage2_notes.setVisibility(View.VISIBLE);
            linearLayoutImage3_notes.setVisibility(View.GONE);
            linearLayoutImage4_notes.setVisibility(View.GONE);
        }

        if (AddEditNotes.byteArray2 == null && AddEditNotes.byteArray4 == null)
            linearLayoutImage4_notes.setVisibility(View.GONE);
    }


    public void checkImage(byte[] byteArray, TextView textViewDeleteImage_notes) {
        if (byteArray != null)
            textViewDeleteImage_notes.setVisibility(View.VISIBLE);
    }


    public void clearImage (Context context, ImageView imageView, TextView textViewDeleteImage) {
        imageView.setImageDrawable(context.getDrawable(R.drawable.no_photo));
        textViewDeleteImage.setVisibility(View.GONE);
    }


}
