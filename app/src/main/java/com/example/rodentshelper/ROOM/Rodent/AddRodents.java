package com.example.rodentshelper.ROOM.Rodent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.exifinterface.media.ExifInterface;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.ImageCompress;
import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Calendar;

public class AddRodents extends Activity {

    EditText editTextNotes, editTextName, editTextFur;
    Button buttonAdd_rodent, buttonEdit_rodent;
    RadioButton radioButtonGender1, radioButtonGender2;

    RadioGroup radioGroup;
    RadioButton radioButton;

    ImageView imageView_rodent, imageViewDate_rodent;

    private TextView textViewDate, textViewDate_hidden;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String dateFormat;

     static byte[] byteArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rodent);

        editTextName = findViewById(R.id.editTextEditName);
        editTextFur = findViewById(R.id.editTextEditFur);
        editTextNotes = findViewById(R.id.editTextEditNotes);

        radioButtonGender1 = findViewById(R.id.radioButtonEditGender1);
        radioButtonGender2 = findViewById(R.id.radioButtonEditGender2);

        radioGroup = findViewById(R.id.radioEditGroup1);

        buttonAdd_rodent = findViewById(R.id.buttonAdd_rodent);
        buttonEdit_rodent = findViewById(R.id.buttonSaveEdit_rodent);


        imageView_rodent = findViewById(R.id.imageView_rodent);
        imageViewDate_rodent = findViewById(R.id.imageViewDate_rodent);


        textViewDate = findViewById(R.id.textViewDate);
        textViewDate_hidden = findViewById(R.id.textViewDate_hidden);

        if (FlagSetup.getFlagRodentAdd() == 1) {
            buttonAdd_rodent.setVisibility(View.VISIBLE);
            buttonEdit_rodent.setVisibility(View.GONE);
        }
        else {
            buttonAdd_rodent.setVisibility(View.GONE);
            buttonEdit_rodent.setVisibility(View.VISIBLE);
        }


        imageView_rodent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent, 3);

            }
        });


        imageViewDate_rodent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateClick();
            }
        });

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateClick();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                String date = day + "/" + month + "/" + year;
                textViewDate.setText(date);

                dateFormat = (year + "-" + month + "-" + day);
                textViewDate_hidden.setText(dateFormat);

            }
        };


        buttonAdd_rodent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRodent();

            }
        });

        if (FlagSetup.getFlagRodentAdd() == 0) {

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            Integer id_animalKey = Integer.parseInt(getIntent().getStringExtra("id_animalKey"));
            String nameKey = getIntent().getStringExtra("nameKey");
            String genderKey = getIntent().getStringExtra("genderKey");
            String birthKey = getIntent().getStringExtra("birthKey");
            String furKey = getIntent().getStringExtra("furKey");
            String notesKey = getIntent().getStringExtra("notesKey");


            if (genderKey.equals("Samiec"))
                radioButtonGender1.setChecked(true);
            if (genderKey.equals("Samica"))
                radioButtonGender2.setChecked(true);


            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO rodentDao = db.dao();

            byte[] byteImage = rodentDao.getImageById(idKey);


            editTextName.setText(nameKey);
            textViewDate.setText(birthKey);
            textViewDate_hidden.setText(birthKey);
            editTextFur.setText(furKey);
            editTextNotes.setText(notesKey);

            byteArray = byteImage;

            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView_rodent.setImageBitmap(bitmap);


            buttonEdit_rodent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int selectedRadio = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedRadio);

                    String stringGender;
                    if (!radioButtonGender1.isChecked() && !radioButtonGender2.isChecked())
                        stringGender = "nie podano";
                    else
                        stringGender = radioButton.getText().toString();


                    dateFormat = textViewDate_hidden.getText().toString();

                    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                    DAO rodentDao = db.dao();
                    rodentDao.updateRodentById(idKey, id_animalKey, editTextName.getText().toString(),
                            stringGender, Date.valueOf(dateFormat),
                            editTextFur.getText().toString(), editTextNotes.getText().toString(), byteArray);


                    viewRodents();

                }
            });
        }


        /*buttonEdit_rodent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewRodents.class));
            }
        });*/

    }

    private void onDateClick() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddRodents.this,
                android.R.style.Theme_Holo_Dialog, dateSetListener, year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private String getRealPathFromURI(Uri contentURI) {

        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return  thePath;
    }



    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

            Uri selectedImageUri = data.getData();

            Drawable res = getResources().getDrawable(R.drawable.loading);
            imageView_rodent.setImageDrawable(res);
            buttonEdit_rodent.setEnabled(false);
            buttonAdd_rodent.setEnabled(false);

            Thread thread = new Thread(() -> {


            Bitmap bitmapTemp = null;
            Bitmap bitmap = null;

            try {
                bitmapTemp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                ImageCompress imageCompress = new ImageCompress();
                bitmap = imageCompress.compressChosenImage(getApplicationContext(), selectedImageUri, bitmapTemp);
            } catch (IOException e) {
                e.printStackTrace();
            }



            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray();

            final Bitmap bitmapView = bitmap;

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        imageView_rodent.setImageBitmap(bitmapView);
                        buttonEdit_rodent.setEnabled(true);
                        buttonAdd_rodent.setEnabled(true);
                    }
                });

            });

            thread.start();


            //Bitmap bitmap1 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            //imageView_rodent.setImageBitmap( bitmap1);



        }

    }





    public void saveRodent() {
        String stringName = editTextName.getText().toString();

        String stringDate = dateFormat;
        String stringFur = editTextFur.getText().toString();
        String stringNotes = editTextNotes.getText().toString();


        int selectedRadio = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedRadio);

        String stringGender;
        if (!radioButtonGender1.isChecked() && !radioButtonGender2.isChecked())
            stringGender = "nie podano";
        else
            stringGender = radioButton.getText().toString();




        if (stringName.length() <= 0 || stringDate == null) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Nie wpisano wymaganych opcji");
            alert.setMessage("Do poprawnego działania aplikacji należy " +
                    "podać imię oraz wiek pupila.\n\nJeśli nie znasz dokładnej " +
                    "daty urodzenia zwierzęcia, możesz podać przybliżoną datę :)");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(AddRodents.this, "Wprowadź wymagane dane", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();



        }
        else {


            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO rodentDao = db.dao();

            if (byteArray == null) {
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.ic_chinchilla);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            }

            rodentDao.insertRecordRodent(new RodentModel(1, stringName, stringGender, Date.valueOf(stringDate), stringFur, stringNotes, byteArray));

            System.out.println("DODANO");
            viewRodents();
        }

    }

    private void viewRodents() {
        finish();
        startActivity(new Intent(getApplicationContext(), ViewRodents.class));
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
        }
        return super.onKeyDown(keyCode, event);
    }



}
