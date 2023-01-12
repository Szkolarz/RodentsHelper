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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.DAORodents;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.SQL.Querries;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
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


        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ActivityLogin.this, ViewRodents.class);
            startActivity(intent);
            finish();
        });


        buttonLogin_login = findViewById(R.id.buttonLogin_login);
        editTextLogin_login = findViewById(R.id.editTextLogin_login);
        editTextPassword_login = findViewById(R.id.editTextPassword_login);
        textViewBadLoginOrPassword_login = findViewById(R.id.textViewBadLoginOrPassword_login);

        try {
            Intent intent = getIntent();
            String loginIntent = intent.getExtras().getString("loginIntent");
            if (!loginIntent.equals(""))
                editTextLogin_login.setText(loginIntent);
        } catch (NullPointerException e) {
            System.out.println("ActivityLogin error - no loginIntent: " + e);
        }



        buttonLogin_login.setOnClickListener(view -> {

            if (isNetworkConnected(ActivityLogin.this)) {
                final ProgressDialog progress = new ProgressDialog(this);
                progress.setTitle("Logowanie...");
                progress.setMessage("Proszę czekać...");
                progress.setCanceledOnTouchOutside(false);
                progress.setCancelable(false);
                progress.show();

                Thread thread = new Thread(() -> {
                    try {
                        if (new AsyncActivity().execute().get()) {
                            runOnUiThread(() -> textViewBadLoginOrPassword_login.setVisibility(View.GONE));

                            String login = editTextLogin_login.getText().toString();
                            String password = editTextPassword_login.getText().toString();

                            if (login.length() <= 0 || password.length() <= 0) {
                                Alerts alert = new Alerts();
                                runOnUiThread(() -> {
                                    progress.cancel();
                                    alert.alertLackOfData("Login i hasło nie mogą zostać puste!", ActivityLogin.this);
                                });
                            } else {

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

                                        SharedPreferences prefsCloudSave = getApplicationContext().getSharedPreferences("prefsCloudSave", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor prefsEditorCloudSave = prefsCloudSave.edit();
                                        prefsEditorCloudSave.putBoolean("prefsCloudSave", true);
                                        prefsEditorCloudSave.apply();


                                        ResultSet resultSetExportDate = dbQuerries.getExportDate(login, ActivityLogin.this);
                                        Date export_date = null;
                                        while (resultSetExportDate.next()) {
                                            export_date = resultSetExportDate.getDate("export_date");
                                        }


                                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                                        DAO dao = db.dao();
                                        dao.insertRecordCloudAccountData(new CloudAccountModel(login, export_date, null));
                                        db.close();

                                        SharedPreferences prefsLoginName = getApplicationContext().getSharedPreferences("prefsLoginName", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor prefsEditorLoginName = prefsLoginName.edit();
                                        prefsEditorLoginName.putString("prefsLoginName", login);
                                        prefsEditorLoginName.apply();

                                        runOnUiThread(() -> {
                                            progress.cancel();
                                            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLogin.this, R.style.AlertDialogStyleUpdate);
                                            alert.setTitle("Pomyślnie zalogowano!");
                                            alert.setMessage(login + ", twoje konto pozostanie zalogowane, nie musisz " +
                                                    "wpisywać kolejny raz hasła.\n\nZapisu do chmury możesz dokonać z zakładki 'Pupile', " +
                                                    "klikając w lewy górny róg i wybierając opcję 'Zapis w chmurze'.");

                                            alert.setPositiveButton("Ok", (dialogInterface, i) -> {
                                                startActivity(new Intent(ActivityLogin.this, ActivityDatabaseManagement.class));
                                                finish();
                                            });
                                            alert.show();
                                        });

                                    } else {
                                        runOnUiThread(() -> {
                                            textViewBadLoginOrPassword_login.setVisibility(View.VISIBLE);
                                            progress.cancel();
                                        });
                                    }

                                } catch (SQLException | InterruptedException e) {
                                    runOnUiThread(progress::cancel);
                                    e.printStackTrace();
                                }
                                runOnUiThread(progress::cancel);
                            }

                        } else {
                            runOnUiThread(() -> {
                                progress.cancel();
                                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLogin.this, R.style.AlertDialogStyleUpdate);
                                alert.setTitle("Nie można się połączyć z serwerem");
                                alert.setMessage("Użyj innej sieci lub spróbuj ponownie później.");
                                alert.setPositiveButton("Rozumiem", (dialogInterface1, i1) -> {
                                    Toast.makeText(ActivityLogin.this, "Brak połączenia z serwerem", Toast.LENGTH_SHORT).show();
                                });
                                alert.show();
                            });
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                });
                thread.start();


            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLogin.this, R.style.AlertDialogStyleUpdate);
                alert.setTitle("Brak połączenia z internetem");
                alert.setMessage("Włącz Wi-Fi lub transmisję danych.");
                alert.setPositiveButton("Rozumiem", (dialogInterface1, i1) ->
                    Toast.makeText(ActivityLogin.this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show());
                alert.show();
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
