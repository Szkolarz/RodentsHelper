package com.gryzoniopedia.rodentshelper.ROOM.Notes.Methods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.gryzoniopedia.rodentshelper.Alerts;
import com.example.rodentshelper.R;
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

import com.squareup.picasso.Picasso;

public class AddEditNotes extends AppCompatActivity {

    private EditText editTextTopic_notes, editTextContent_notes;


    private Button buttonAdd_notes, buttonSaveEdit_notes;
    private TextView textViewDeleteImage1_notes, textViewDeleteImage2_notes,
            textViewDeleteImage3_notes, textViewDeleteImage4_notes;
    private View viewImages_notes;
    private LinearLayout linearLayoutImage1_notes, linearLayoutImage2_notes,
            linearLayoutImage3_notes, linearLayoutImage4_notes;
    private ImageView imageView1_notes, imageView2_notes, imageView3_notes, imageView4_notes;
    public static byte[] byteArray1 = null, byteArray2 = null, byteArray3 = null, byteArray4 = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_item_list);

        ScrollView scrollViewNotes = findViewById(R.id.scrollViewNotes);

        ScrollView.LayoutParams scrollViewLayoutParams = new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollViewNotes.setLayoutParams(scrollViewLayoutParams);

        LinearLayout linearLayoutCard_notes = findViewById(R.id.linearLayoutCard_notes);
        LinearLayout linearLayoutED_notes = findViewById(R.id.linearLayoutED_notes);

        linearLayoutCard_notes.setBackgroundColor(Color.parseColor("#ACC5E1"));
        linearLayoutED_notes.setVisibility(View.GONE);

        textViewDeleteImage1_notes = findViewById(R.id.textViewDeleteImage1_notes);
        textViewDeleteImage2_notes = findViewById(R.id.textViewDeleteImage2_notes);
        textViewDeleteImage3_notes = findViewById(R.id.textViewDeleteImage3_notes);
        textViewDeleteImage4_notes = findViewById(R.id.textViewDeleteImage4_notes);

        linearLayoutImage1_notes = findViewById(R.id.linearLayoutImage1_notes);
        linearLayoutImage2_notes = findViewById(R.id.linearLayoutImage2_notes);
        linearLayoutImage3_notes = findViewById(R.id.linearLayoutImage3_notes);
        linearLayoutImage4_notes = findViewById(R.id.linearLayoutImage4_notes);

        linearLayoutImage1_notes.setVisibility(View.VISIBLE);
        viewImages_notes = findViewById(R.id.viewImages_notes);


        NotesImages notesImages = new NotesImages();
        notesImages.setUpImageOrder(textViewDeleteImage1_notes, textViewDeleteImage2_notes, textViewDeleteImage3_notes,
                textViewDeleteImage4_notes, viewImages_notes, linearLayoutImage2_notes, linearLayoutImage3_notes,
                linearLayoutImage4_notes);

        TextView textViewDate_notes, textViewDateHidden_notes, textViewRequired_notes;

        LinearLayout linearLayoutToolbar = findViewById(R.id.linearLayoutToolbar);
        linearLayoutToolbar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar_main);

        editTextTopic_notes = findViewById(R.id.editTextTopic_notes);
        editTextContent_notes = findViewById(R.id.editTextContent_notes);

        ImageView buttonEdit_notes = findViewById(R.id.buttonEdit_notes);
        buttonAdd_notes = findViewById(R.id.buttonAdd_notes);
        buttonSaveEdit_notes = findViewById(R.id.buttonSaveEdit_notes);
        ImageView buttonDelete_notes = findViewById(R.id.buttonDelete_notes);

        imageView1_notes = findViewById(R.id.imageView1_notes);
        imageView2_notes = findViewById(R.id.imageView2_notes);
        imageView3_notes = findViewById(R.id.imageView3_notes);
        imageView4_notes = findViewById(R.id.imageView4_notes);

        imageView1_notes.setOnClickListener(view -> {
            Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });
        imageView2_notes.setOnClickListener(view -> {
            Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        });
        imageView3_notes.setOnClickListener(view -> {
            Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 3);
        });
        imageView4_notes.setOnClickListener(view -> {
            Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 4);
        });

        textViewDeleteImage1_notes.setOnClickListener(view -> {
            byteArray1 = null;
            setUpTextViewDelete(imageView1_notes, textViewDeleteImage1_notes);
        });
        textViewDeleteImage2_notes.setOnClickListener(view -> {
            byteArray2 = null;
            setUpTextViewDelete(imageView2_notes, textViewDeleteImage2_notes);
        });
        textViewDeleteImage3_notes.setOnClickListener(view -> {
            byteArray3 = null;
            setUpTextViewDelete(imageView3_notes, textViewDeleteImage3_notes);
        });
        textViewDeleteImage4_notes.setOnClickListener(view -> {
            byteArray4 = null;
            setUpTextViewDelete(imageView4_notes, textViewDeleteImage4_notes);
        });

        textViewDate_notes = findViewById(R.id.textViewDate_notes);
        textViewDateHidden_notes = findViewById(R.id.textViewDateHidden_notes);
        textViewRequired_notes = findViewById(R.id.textViewRequired_notes);

        textViewDeleteImage1_notes = findViewById(R.id.textViewDeleteImage1_notes);
        textViewDeleteImage2_notes = findViewById(R.id.textViewDeleteImage2_notes);
        textViewDeleteImage3_notes = findViewById(R.id.textViewDeleteImage3_notes);
        textViewDeleteImage4_notes = findViewById(R.id.textViewDeleteImage4_notes);

        boolean isEdit = getIntent().getBooleanExtra("flagKey", false);

        if (!isEdit) {
            buttonAdd_notes.setVisibility(View.VISIBLE);
            buttonEdit_notes.setVisibility(View.GONE);
            buttonDelete_notes.setVisibility(View.GONE);
            buttonSaveEdit_notes.setVisibility(View.GONE);
            toolbar.setTitle("Dodawanie notatki");

            Date dateGet = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(dateGet);
            textViewDateHidden_notes.setText(formattedDate);
            textViewDate_notes.setText(DateFormat.formatDate(java.sql.Date.valueOf(formattedDate)));
        } else {
            buttonAdd_notes.setVisibility(View.GONE);
            buttonEdit_notes.setVisibility(View.GONE);
            buttonDelete_notes.setVisibility(View.GONE);
            buttonSaveEdit_notes.setVisibility(View.VISIBLE);
            toolbar.setTitle("Edycja notatki");

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            String id_animalKey = (getIntent().getStringExtra("id_animalKey"));
            String topicKey = getIntent().getStringExtra("topicKey");
            String contentKey = getIntent().getStringExtra("contentKey");
            String create_dateKey = getIntent().getStringExtra("create_dateKey");

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotes daoNotes = db.daoNotes();

            byteArray1 = daoNotes.getNotesImage1ById(idKey);
            byteArray2 = daoNotes.getNotesImage2ById(idKey);
            byteArray3 = daoNotes.getNotesImage3ById(idKey);
            byteArray4 = daoNotes.getNotesImage4ById(idKey);

            notesImages.checkImage(byteArray1, textViewDeleteImage1_notes);
            notesImages.checkImage(byteArray2, textViewDeleteImage2_notes);
            notesImages.checkImage(byteArray3, textViewDeleteImage3_notes);
            notesImages.checkImage(byteArray4, textViewDeleteImage4_notes);

            db.close();

            notesImages.setUpImageOrder(textViewDeleteImage1_notes, textViewDeleteImage2_notes, textViewDeleteImage3_notes,
                    textViewDeleteImage4_notes, viewImages_notes, linearLayoutImage2_notes, linearLayoutImage3_notes,
                    linearLayoutImage4_notes);

            displayImage(byteArray1, imageView1_notes);
            displayImage(byteArray2, imageView2_notes);
            displayImage(byteArray3, imageView3_notes);
            displayImage(byteArray4, imageView4_notes);

            editTextTopic_notes.setText(topicKey);
            editTextContent_notes.setText(contentKey);
            textViewDate_notes.setText( DateFormat.formatDate(java.sql.Date.valueOf(create_dateKey)));

            buttonSaveEdit_notes.setOnClickListener(view -> saveEditNotes(textViewRequired_notes));
        }

        buttonAdd_notes.setOnClickListener(view -> saveNotes(textViewDateHidden_notes, textViewRequired_notes));

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(AddEditNotes.this, ViewNotes.class);
            startActivity(intent);
            finish();
        });
    }

    private void displayImage (byte[] byteArray, ImageView imageView) {
        if (byteArray != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bitmap);
        } else
            imageView.setImageDrawable(getDrawable(R.drawable.ic_camera));
    }

    private void setUpTextViewDelete (ImageView imageView, TextView textViewDeleteImage) {

        imageView.setImageDrawable(getDrawable(R.drawable.ic_camera));
        textViewDeleteImage.setVisibility(View.GONE);

        NotesImages notesImages = new NotesImages();
        notesImages.setUpImageOrder(textViewDeleteImage1_notes, textViewDeleteImage2_notes, textViewDeleteImage3_notes,
                textViewDeleteImage4_notes, viewImages_notes, linearLayoutImage2_notes, linearLayoutImage3_notes,
                linearLayoutImage4_notes);
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

            Uri selectedImageUri = data.getData();
            NotesImages notesImages = new NotesImages();

            notesImages.displayImage(this, imageView1_notes, imageView2_notes, imageView3_notes,
                    imageView4_notes, selectedImageUri, requestCode,
                    buttonAdd_notes, buttonSaveEdit_notes, textViewDeleteImage1_notes, textViewDeleteImage2_notes,
                    textViewDeleteImage3_notes, textViewDeleteImage4_notes,
                    viewImages_notes, linearLayoutImage2_notes,
                    linearLayoutImage3_notes, linearLayoutImage4_notes);
        }

    }




    public void saveNotes(TextView textViewDateHidden_notes, TextView textViewRequired_notes) {

        NotesImages notesImages = new NotesImages();
        notesImages.setUpImageOrder(textViewDeleteImage1_notes, textViewDeleteImage2_notes, textViewDeleteImage3_notes,
                textViewDeleteImage4_notes, viewImages_notes, linearLayoutImage2_notes, linearLayoutImage3_notes,
                linearLayoutImage4_notes);

        String topicKey = editTextTopic_notes.getText().toString();
        String contentKey = editTextContent_notes.getText().toString();

        if (contentKey.equals("") ) {
            textViewRequired_notes.setVisibility(View.VISIBLE);
            Alerts alert = new Alerts();
            alert.alertLackOfData("Należy wpisać treść notatki", this);
        } else {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotes dao = db.daoNotes();

            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

            dao.insertRecordNotes(new NotesModel(prefsGetRodentId.getInt("rodentId", 0), topicKey, contentKey,
                    java.sql.Date.valueOf(textViewDateHidden_notes.getText().toString()), null, byteArray1,
                    byteArray2, byteArray3, byteArray4));
            db.close();
            System.out.println("DODANO");
            byteArray1 = null; byteArray2 = null; byteArray3 = null; byteArray4 = null;
            viewNotes();
        }

    }

    public void saveEditNotes(TextView textViewRequired_notes) {
        if (editTextContent_notes.getText().toString().equals("") ) {
            textViewRequired_notes.setVisibility(View.VISIBLE);
            Alerts alert = new Alerts();
            alert.alertLackOfData("Należy wpisać treść notatki", this);
        } else {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotes dao = db.daoNotes();

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            dao.updateNotesById(idKey, editTextTopic_notes.getText().toString(), editTextContent_notes.getText().toString(),
                    byteArray1, byteArray2, byteArray3, byteArray4);
            db.close();

            viewNotes();
        }
    }


    private void viewNotes() {
        startActivity(new Intent(getApplicationContext(), ViewNotes.class));
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(AddEditNotes.this, ViewNotes.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }




}
