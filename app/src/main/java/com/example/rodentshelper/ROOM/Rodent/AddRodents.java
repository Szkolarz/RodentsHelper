package com.example.rodentshelper.ROOM.Rodent;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.ImageCompress;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAORodents;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddRodents extends AppCompatActivity {

    private EditText editTextNotes, editTextName, editTextFur;
    private Button buttonAdd_rodent, buttonEdit_rodent;
    private ImageView buttonDelete_rodent, imageView_rodent;
    private TextView textViewDeleteImage_rodent, textViewRequired_rodent1, textViewRequired_rodent2,
            textViewDate, textViewDate_hidden;
    private RadioButton radioButtonGender1, radioButtonGender2, radioButton;
    private RadioGroup radioGroup;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String dateFormat;

    byte[] byteArray;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rodent);

        Toolbar toolbar = findViewById(R.id.toolbar_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAORodents daoRodents = db.daoRodents();


        editTextName = findViewById(R.id.editTextEditName);
        editTextFur = findViewById(R.id.editTextEditFur);
        editTextNotes = findViewById(R.id.editTextEditNotes);

        radioButtonGender1 = findViewById(R.id.radioButtonEditGender1);
        radioButtonGender2 = findViewById(R.id.radioButtonEditGender2);

        radioGroup = findViewById(R.id.radioEditGroup1);

        buttonAdd_rodent = findViewById(R.id.buttonAdd_rodent);
        buttonEdit_rodent = findViewById(R.id.buttonSaveEdit_rodent);
        buttonDelete_rodent = findViewById(R.id.buttonDelete_rodent);
        textViewDeleteImage_rodent = findViewById(R.id.textViewDeleteImage_rodent);
        textViewRequired_rodent1 = findViewById(R.id.textViewRequired_rodent1);
        textViewRequired_rodent2 = findViewById(R.id.textViewRequired_rodent2);


        imageView_rodent = findViewById(R.id.imageView_rodent);
        ImageButton imageButtonDate_rodent = findViewById(R.id.imageButtonDate_rodent);


        textViewDate = findViewById(R.id.textViewDate);
        textViewDate_hidden = findViewById(R.id.textViewDate_hidden);

        if (FlagSetup.getFlagRodentAdd() == 1) {
            toolbar.setTitle("Dodawanie zwierzęcia");
            buttonAdd_rodent.setVisibility(View.VISIBLE);
            buttonEdit_rodent.setVisibility(View.GONE);
            buttonDelete_rodent.setVisibility(View.GONE);
        }
        else {
            toolbar.setTitle("Edytowanie zwierzęcia");
            buttonAdd_rodent.setVisibility(View.GONE);
            buttonEdit_rodent.setVisibility(View.VISIBLE);
        }


        imageView_rodent.setOnClickListener(view -> {
            // Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(intent, 3);

        });

        textViewDeleteImage_rodent.setOnClickListener(view -> {
            imageView_rodent.setImageDrawable(getDrawable(R.drawable.ic_chinchilla));
            byteArray = null;
            textViewDeleteImage_rodent.setVisibility(View.GONE);

        });


        imageButtonDate_rodent.setOnClickListener(view -> onDateClick());

        textViewDate.setOnClickListener(view -> onDateClick());

        dateSetListener = (datePicker, year, month, day) -> {
            month += 1;

            String date = day + "/" + month + "/" + year;
            textViewDate.setText(date);

            dateFormat = (year + "-" + month + "-" + day);
            textViewDate_hidden.setText(dateFormat);

        };

        buttonDelete_rodent.setOnClickListener(view -> {

            AlertDialog.Builder alert = new AlertDialog.Builder(buttonDelete_rodent.getContext(), R.style.AlertDialogStyle);
            alert.setTitle("Usuwanie pupila");
            alert.setMessage("Czy na pewno chcesz usunąć pupila z listy?\n\nProces jest nieodwracalny!");

            alert.setPositiveButton("Tak", (dialogInterface, i) -> {
                Toast.makeText(getApplicationContext(), "Pomyślnie usunięto", Toast.LENGTH_SHORT).show();



                Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));

                //rodentDao.DeleteAllRodentsVetsByRodent(idKey);
                daoRodents.deleteRodentById(idKey);


                viewRodents();

            });
            alert.setNegativeButton("Nie", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "Anulowano", Toast.LENGTH_SHORT).show());

            alert.create().show();


        });


        buttonAdd_rodent.setOnClickListener(view -> saveRodent());

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



            byte[] byteImage = daoRodents.getImageById(idKey);


            editTextName.setText(nameKey);
            textViewDate.setText(birthKey);
            textViewDate_hidden.setText(birthKey);
            editTextFur.setText(furKey);
            editTextNotes.setText(notesKey);

            byteArray = byteImage;

            checkImage();

            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView_rodent.setImageBitmap(bitmap);


            buttonEdit_rodent.setOnClickListener(view -> onClickSaveEdit(daoRodents, idKey, id_animalKey, nameKey));
        }


        /*buttonEdit_rodent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewRodents.class));
            }
        });*/

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(AddRodents.this, ViewRodents.class);
            startActivity(intent);
            finish();
        });

        db.close();
    }

    private void onDateClick() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddRodents.this,
                android.R.style.Theme_Holo_Dialog, dateSetListener, year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
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
            buttonAdd_rodent.setBackgroundColor(Color.GRAY);
            buttonAdd_rodent.setTextColor(Color.BLACK);
            buttonEdit_rodent.setTextColor(Color.BLACK);
            buttonEdit_rodent.setBackgroundColor(Color.GRAY);

            Thread thread = new Thread(() -> {


            Bitmap bitmapTemp;
            Bitmap bitmap = null;

            try {
                bitmapTemp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                ImageCompress imageCompress = new ImageCompress();
                bitmap = imageCompress.compressChosenImage(getApplicationContext(), selectedImageUri, bitmapTemp);
            } catch (IOException e) {
                e.printStackTrace();
            }



            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            } catch (NullPointerException e) {
               System.out.println("Bitmap error " + e);
            }
            byteArray = stream.toByteArray();

            final Bitmap bitmapView = bitmap;

                runOnUiThread(() -> {

                    imageView_rodent.setImageBitmap(bitmapView);

                    buttonEdit_rodent.setEnabled(true);
                    buttonAdd_rodent.setEnabled(true);
                    buttonAdd_rodent.setBackgroundColor(Color.parseColor("#5397DF"));
                    buttonEdit_rodent.setBackgroundColor(Color.parseColor("#5397DF"));
                    buttonAdd_rodent.setTextColor(Color.WHITE);
                    buttonEdit_rodent.setTextColor(Color.WHITE);
                    checkImage();
                });

            });

            thread.start();


            //Bitmap bitmap1 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            //imageView_rodent.setImageBitmap( bitmap1);


        }

    }

    @SuppressLint("SuspiciousIndentation")
    private void checkImage() {
        if (byteArray != null)
            textViewDeleteImage_rodent.setVisibility(View.VISIBLE);
    }


    private boolean checkNameAvailability(String name, boolean isFromEdit, String nameKey) {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAORodents daoRodents = db.daoRodents();
        List<String> names = daoRodents.getAllNameRodents();
        db.close();

        int flag = 0;
        for (int i=0; i<names.size(); i++) {

            //when name from editText is the same as name from database
            if (name.equals(names.get(i))) {
                if (!isFromEdit)
                    return false;
                else {
                    //when user edits animal without editing its name
                    if (nameKey.equals(name)) {
                        flag++;
                        if (flag == 2)
                            return false;
                    //when user edits animal but has changed its name
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void saveRodent() {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAORodents daoRodents = db.daoRodents();

        String stringName = editTextName.getText().toString();
        String stringDate = dateFormat;
        String stringFur = editTextFur.getText().toString();
        String stringNotes = editTextNotes.getText().toString();


        int selectedRadio = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedRadio);

        String stringGender;
        if (!radioButtonGender1.isChecked() && !radioButtonGender2.isChecked())
            stringGender = "nie podano";
        else
            stringGender = radioButton.getText().toString();



        if (stringName.length() <= 0 || stringDate == null) {
            textViewRequired_rodent1.setVisibility(View.VISIBLE);
            textViewRequired_rodent2.setVisibility(View.VISIBLE);

            Alerts alert = new Alerts();
            alert.alertLackOfData("Do poprawnego działania aplikacji należy " +
                    "podać imię oraz wiek pupila.\n\nJeśli nie znasz dokładnej " +
                    "daty urodzenia zwierzęcia, możesz podać przybliżoną datę.", this);
        } else if (!checkNameAvailability(stringName.trim(), false, "")) {
            Alerts alert = new Alerts();
            alert.simpleInfo("Imię już istnieje", "Aplikacja zakłada, że nie mogą istnieć " +
                    "dokładnie dwa takie same imiona pupili.\n\nJeśli rzeczywiście posiadasz dwa zwierzęta " +
                    "z tym samym imieniem, możesz je lekko zmienić, np. '" + stringName.trim() + " (szczur)', albo " +
                    "'" + stringName.trim() + " 2'.", this);
        } else {
            if (byteArray == null) {
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.ic_chinchilla);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            }

            SharedPreferences prefsFirstStart = getApplicationContext().getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

            daoRodents.insertRecordRodent(new RodentModel(prefsFirstStart.getInt("prefsFirstStart", 0), stringName.trim(), stringGender, Date.valueOf(stringDate), stringFur, stringNotes, byteArray));

            Toast.makeText(getApplicationContext(), "Pomyślnie dodano", Toast.LENGTH_SHORT).show();
            viewRodents();
        }

        db.close();

    }

    private void onClickSaveEdit(DAORodents daoRodents, Integer idKey, Integer id_animalKey, String nameKey) {

        if (editTextName.getText().toString().length() <= 0) {
            textViewRequired_rodent1.setVisibility(View.VISIBLE);
            textViewRequired_rodent2.setVisibility(View.VISIBLE);

            Alerts alert = new Alerts();
            alert.alertLackOfData("Do poprawnego działania aplikacji należy " +
                    "podać imię pupila.", this);
        } else if (!checkNameAvailability(editTextName.getText().toString().trim(), true, nameKey)) {
            Alerts alert = new Alerts();
            alert.simpleInfo("Imię już istnieje", "Aplikacja zakłada, że nie mogą istnieć " +
                    "dokładnie dwa takie same imiona pupili.\n\nJeśli rzeczywiście posiadasz dwa zwierzęta " +
                    "z tym samym imieniem, możesz je lekko zmienić, np. '" + editTextName.getText().toString().trim() + " (szczur)', albo " +
                    "'" + editTextName.getText().toString().trim() + " 2'.", this);
        } else {

            int selectedRadio = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedRadio);

            String stringGender;
            if (!radioButtonGender1.isChecked() && !radioButtonGender2.isChecked())
                stringGender = "nie podano";
            else
                stringGender = radioButton.getText().toString();


            dateFormat = textViewDate_hidden.getText().toString();

            if (byteArray == null) {
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.ic_chinchilla);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            }

            daoRodents.updateRodentById(idKey, id_animalKey, editTextName.getText().toString().trim(),
                    stringGender, Date.valueOf(dateFormat),
                    editTextFur.getText().toString(), editTextNotes.getText().toString(), byteArray);


            viewRodents();
        }

    }

    private void viewRodents() {
        startActivity(new Intent(getApplicationContext(), ViewRodents.class));
        finish();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
        }
        return super.onKeyDown(keyCode, event);
    }



}
