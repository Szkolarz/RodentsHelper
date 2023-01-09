package com.example.rodentshelper.DatabaseManagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.SQL.Querries;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ActivityRegister extends AppCompatActivity {

    private Button buttonRegister_register;
    private EditText editTextLogin_register, editTextPassword_register, editTextRepeatPassword_register;
    private TextView textViewLoginExists_register, textViewBadPassword_register, textViewPasswordsNotMatch_register,
            textViewLoginTooShort_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Rejestracja konta");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView textView1_rodent;
        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegister.this, ViewRodents.class);
                startActivity(intent);
                finish();
            }
        });


        buttonRegister_register = findViewById(R.id.buttonRegister_register);

        editTextLogin_register = findViewById(R.id.editTextLogin_register);
        editTextPassword_register = findViewById(R.id.editTextPassword_register);
        editTextRepeatPassword_register = findViewById(R.id.editTextRepeatPassword_register);
        textViewLoginExists_register = findViewById(R.id.textViewLoginExists_register);
        textViewBadPassword_register = findViewById(R.id.textViewBadPassword_register);
        textViewPasswordsNotMatch_register = findViewById(R.id.textViewPasswordsNotMatch_register);
        textViewLoginTooShort_register = findViewById(R.id.textViewLoginTooShort_register);




        buttonRegister_register.setOnClickListener(view -> {
            String login = editTextLogin_register.getText().toString();
            String password = editTextPassword_register.getText().toString();
            String passwordRepeat = editTextRepeatPassword_register.getText().toString();

            textViewLoginExists_register.setVisibility(View.GONE);
            textViewBadPassword_register.setVisibility(View.GONE);
            textViewPasswordsNotMatch_register.setVisibility(View.GONE);
            textViewLoginTooShort_register.setVisibility(View.GONE);

            if (isValid(login, password, passwordRepeat, ActivityRegister.this)) {

                Querries dbQuerries = new Querries();

                ResultSet resultSetRegister = null;
                try {
                    resultSetRegister = dbQuerries.checkLoginAvailability(ActivityRegister.this);


                    boolean isLoginAvailable = true;

                    while (resultSetRegister.next()) {
                        if (login.equals(resultSetRegister.getString("login"))) {
                            textViewLoginExists_register.setVisibility(View.VISIBLE);
                            isLoginAvailable = false;
                        }

                    }

                    if (isLoginAvailable) {

                        //BCrypt hashing
                        String hashedPassword = BCrypt.withDefaults().hashToString(12,
                                editTextPassword_register.getText().toString().toCharArray());

                /*BCrypt.Result result = BCrypt.verifyer().verify(editTextPassword_register.getText().toString().toCharArray(),
                        hashedPassword);
                if (result == true) {
                    działa
                }*/

                        dbQuerries.registerNewAccount(editTextLogin_register.getText().toString(),
                                hashedPassword, ActivityRegister.this);
                    } else {

                    }

                } catch (SQLException | InterruptedException e) {
                    throw new RuntimeException(e);

                }
            }
        });



        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

    }



    public boolean isValid (String login, String password, String passwordRepeat, Context context) {

        Pattern letterPattern = Pattern.compile("[a-zA-Z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");


        boolean flag = true;

        if (login.length() <= 0 || password.length() <= 0 || passwordRepeat.length() <= 0) {
            Alerts alert = new Alerts();
            alert.alertLackOfData("Wszystkie pola muszą być uzupełnione.", context);
            flag = false;
        }

        if (login.length() < 4) {
            textViewLoginTooShort_register.setVisibility(View.VISIBLE);
            flag = false;
        }

        if (!password.equals(passwordRepeat)) {
            textViewPasswordsNotMatch_register.setVisibility(View.VISIBLE);
            flag = false;
        }

        if ((password.length() < 8) || (!letterPattern.matcher(password).find()) ||
                (!digitCasePatten.matcher(password).find())) {
            textViewBadPassword_register.setVisibility(View.VISIBLE);
            flag = false;
        }


        return flag;

    }




}