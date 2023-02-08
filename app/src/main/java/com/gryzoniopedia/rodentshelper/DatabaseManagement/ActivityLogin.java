package com.gryzoniopedia.rodentshelper.DatabaseManagement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.ActivityProgressBar;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;
import com.gryzoniopedia.rodentshelper.SQL.Querries;
import com.gryzoniopedia.rodentshelper.Alerts;
import com.gryzoniopedia.rodentshelper.AsyncActivity;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ActivityLogin extends AppCompatActivity {

    private Boolean allowBackButton = true;

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

        Button buttonLogin_login = findViewById(R.id.buttonLogin_login);
        EditText editTextLogin_login = findViewById(R.id.editTextLogin_login);
        EditText editTextPassword_login = findViewById(R.id.editTextPassword_login);
        TextView textViewBadLoginOrPassword_login = findViewById(R.id.textViewBadLoginOrPassword_login);

        LinearLayout linearLayoutLoginProgress = findViewById(R.id.linearLayoutLoginProgress);
        ProgressBar progressBarLogin = findViewById(R.id.progressBarLogin);

        try {
            Intent intent = getIntent();
            String loginIntent = intent.getExtras().getString("loginIntent");
            if (!loginIntent.equals(""))
                editTextLogin_login.setText(loginIntent);
        } catch (NullPointerException e) {
            System.out.println("ActivityLogin error - no loginIntent: " + e);
        }



        buttonLogin_login.setOnClickListener(view -> {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();

            if (isNetworkConnected(ActivityLogin.this)) {

                Thread threadProgressBar = new Thread(() -> runOnUiThread(() -> {
                    allowBackButton = false;
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressBarLogin.setIndeterminate(true);
                    linearLayoutLoginProgress.setVisibility(View.VISIBLE);
                }));
                threadProgressBar.start();

                Thread thread = new Thread(() -> {
                    StrictMode.setThreadPolicy(policy);
                    try {
                        if (new AsyncActivity().execute().get()) {
                            runOnUiThread(() -> textViewBadLoginOrPassword_login.setVisibility(View.GONE));

                            String login = editTextLogin_login.getText().toString();
                            String password = editTextPassword_login.getText().toString();

                            if (login.length() <= 0 || password.length() <= 0) {
                                Alerts alert = new Alerts();
                                runOnUiThread(() -> {
                                    linearLayoutLoginProgress.setVisibility(View.GONE); allowBackButton = true;
                                    this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    alert.alertLackOfData("Login i hasło nie mogą zostać puste!", ActivityLogin.this);
                                });
                            } else {

                                Querries dbQuerries = new Querries();

                                ResultSet resultSetLogin;
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
                                            linearLayoutLoginProgress.setVisibility(View.GONE); allowBackButton = true;
                                            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLogin.this, R.style.AlertDialogStyleUpdate);
                                            alert.setTitle("Pomyślnie zalogowano!");
                                            alert.setMessage(login + ", twoje konto pozostanie zalogowane, nie musisz " +
                                                    "wpisywać kolejny raz hasła.\n\nZapisu do chmury możesz dokonać z zakładki 'Pupile', " +
                                                    "klikając w lewy górny róg i wybierając opcję 'Zapis w chmurze'.");

                                            alert.setPositiveButton("Ok", (dialogInterface, i) -> {
                                                startActivity(new Intent(ActivityLogin.this, ActivityDatabaseManagement.class));
                                                finish();
                                            });
                                            alert.setOnCancelListener(dialog -> {
                                                startActivity(new Intent(ActivityLogin.this, ActivityDatabaseManagement.class));
                                                finish();
                                            });
                                            alert.show();
                                        });

                                    } else {
                                        runOnUiThread(() -> {
                                            textViewBadLoginOrPassword_login.setVisibility(View.VISIBLE);
                                            linearLayoutLoginProgress.setVisibility(View.GONE); allowBackButton = true;
                                            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        });
                                    }

                                } catch (SQLException | InterruptedException e) {
                                    runOnUiThread(() -> {
                                        linearLayoutLoginProgress.setVisibility(View.GONE); allowBackButton = true;
                                        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    });

                                    Log.e("191 ActivityLogin", Log.getStackTraceString(e));
                                }
                                runOnUiThread(() -> {
                                    linearLayoutLoginProgress.setVisibility(View.GONE); allowBackButton = true;
                                    this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                });
                            }

                        } else {
                            runOnUiThread(() -> {
                                linearLayoutLoginProgress.setVisibility(View.GONE); allowBackButton = true;
                                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLogin.this, R.style.AlertDialogStyleUpdate);
                                alert.setTitle("Nie można się połączyć z serwerem");
                                alert.setMessage("Użyj innej sieci lub spróbuj ponownie później.");
                                alert.setPositiveButton("Rozumiem", (dialogInterface1, i1) -> Toast.makeText(ActivityLogin.this, "Brak połączenia z serwerem", Toast.LENGTH_SHORT).show());
                                alert.show();
                            });
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        Log.e("ActivityLogin", Log.getStackTraceString(e));
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
    public void onBackPressed() {
        if (allowBackButton) {
            Intent intent = new Intent(ActivityLogin.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
    }

}
