package com.example.rodentshelper.DatabaseManagement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.SQL.Querries;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ActivityLogin extends AppCompatActivity {

    private Button buttonLogin_login;

    private EditText editTextLogin_login, editTextPassword_login;
    private TextView textViewBadLoginOrPassword_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Logowanie");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ViewRodents.class);
                startActivity(intent);
                finish();
            }
        });


        buttonLogin_login = findViewById(R.id.buttonLogin_login);

        editTextLogin_login = findViewById(R.id.editTextLogin_login);
        editTextPassword_login = findViewById(R.id.editTextPassword_login);
        textViewBadLoginOrPassword_login = findViewById(R.id.textViewBadLoginOrPassword_login);



        buttonLogin_login.setOnClickListener(view -> {

            if (isNetworkConnected(ActivityLogin.this)) {

                final ProgressDialog progress = new ProgressDialog(this);

                progress.setTitle("Logowanie...");
                progress.setMessage("Proszę czekać...");
                progress.setCanceledOnTouchOutside(false);
                progress.setCancelable(false);
                progress.show();

                try {
                    if (new AsyncActivity().execute().get()) {

                        Thread thread = new Thread(() -> runOnUiThread(() -> {

                            String login = editTextLogin_login.getText().toString();
                            String password = editTextPassword_login.getText().toString();

                            textViewBadLoginOrPassword_login.setVisibility(View.GONE);

                                Querries dbQuerries = new Querries();

                                ResultSet resultSetLogin = null;
                                try {
                                    resultSetLogin = dbQuerries.checkLoginAndPassword(ActivityLogin.this);

                                    boolean haveUserLogged = false;
                                    while (resultSetLogin.next()) {

                                        if (login.equals(resultSetLogin.getString("login"))) {
                                            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(),
                                                    resultSetLogin.getString("password"));

                                            if (result.verified) {
                                                haveUserLogged = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (haveUserLogged) {

                                        progress.cancel();

                                        SharedPreferences prefsCloudSave = getApplicationContext().getSharedPreferences("prefsCloudSave", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor prefsEditorCloudSave = prefsCloudSave.edit();
                                        prefsEditorCloudSave.putBoolean("prefsCloudSave", true);
                                        prefsEditorCloudSave.apply();

                                        SharedPreferences prefsLoginName = getApplicationContext().getSharedPreferences("prefsLoginName", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor prefsEditorLoginName = prefsLoginName.edit();
                                        prefsEditorLoginName.putString("prefsLoginName", login);
                                        prefsEditorLoginName.apply();

                                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLogin.this, R.style.AlertDialogStyleUpdate);
                                        alert.setTitle("Pomyślnie zalogowano!");
                                        alert.setMessage(login + ", twoje konto pozostanie zalogowane, nie musisz " +
                                                "wpisywać kolejny raz hasła. Zapisu do chmury możesz dokonać z zakładki 'Pupile', " +
                                                "klikając w lewy górny róg i wybierając opcję 'Zapis w chmurze'.");

                                        alert.setPositiveButton("Ok", (dialogInterface, i) -> {
                                            startActivity(new Intent(ActivityLogin.this, ActivityDatabaseManagement.class));
                                            finish();
                                        });
                                        alert.show();

                                    } else {
                                        textViewBadLoginOrPassword_login.setVisibility(View.VISIBLE);
                                    }

                                } catch (SQLException | InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                progress.cancel();

                        }));

                        thread.start();

                    } else {
                        System.out.println("ni działa");
                    }
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }



            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLogin.this, R.style.AlertDialogStyleUpdate);
                alert.setTitle("Brak połączenia z internetem");
                alert.setMessage("Użyj innej sieci lub spróbuj ponownie później.");

            }
        });



        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

    }



    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ActivityLogin.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
