package com.gryzoniopedia.rodentshelper.DatabaseManagement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;
import com.gryzoniopedia.rodentshelper.SQL.Querries;
import com.gryzoniopedia.rodentshelper.Alerts;
import com.gryzoniopedia.rodentshelper.AsyncActivity;
import com.example.rodentshelper.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ActivityRegister extends AppCompatActivity {


    private TextView textViewBadPassword_register, textViewPasswordsNotMatch_register,
            textViewLoginTooShort_register;

    private Boolean allowBackButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Rejestracja konta");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);


        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ActivityRegister.this, ViewRodents.class);
            startActivity(intent);
            finish();
        });


        Button buttonRegister_register = findViewById(R.id.buttonRegister_register);
        EditText editTextLogin_register = findViewById(R.id.editTextLogin_register);
        EditText editTextPassword_register = findViewById(R.id.editTextPassword_register);
        EditText editTextRepeatPassword_register = findViewById(R.id.editTextRepeatPassword_register);
        TextView textViewLoginExists_register = findViewById(R.id.textViewLoginExists_register);
        textViewBadPassword_register = findViewById(R.id.textViewBadPassword_register);
        textViewPasswordsNotMatch_register = findViewById(R.id.textViewPasswordsNotMatch_register);
        textViewLoginTooShort_register = findViewById(R.id.textViewLoginTooShort_register);

        LinearLayout linearLayoutRegisterProgress = findViewById(R.id.linearLayoutRegisterProgress);
        ProgressBar progressBarRegister = findViewById(R.id.progressBarRegister);


        buttonRegister_register.setOnClickListener(view -> {

            textViewLoginExists_register.setVisibility(View.GONE);
            textViewBadPassword_register.setVisibility(View.GONE);
            textViewPasswordsNotMatch_register.setVisibility(View.GONE);
            textViewLoginTooShort_register.setVisibility(View.GONE);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();

            if (isNetworkConnected(ActivityRegister.this)) {

                Thread threadProgressBar = new Thread(() -> runOnUiThread(() -> {
                    allowBackButton = false;
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressBarRegister.setIndeterminate(true);
                    linearLayoutRegisterProgress.setVisibility(View.VISIBLE);
                }));
                threadProgressBar.start();


                Thread thread = new Thread(() -> {
                    StrictMode.setThreadPolicy(policy);
                    try {
                        if (new AsyncActivity().execute().get()) {

                            String login = editTextLogin_register.getText().toString();
                            String password = editTextPassword_register.getText().toString();
                            String passwordRepeat = editTextRepeatPassword_register.getText().toString();


                                if (isValid(login, password, passwordRepeat, ActivityRegister.this)) {

                                    Querries dbQuerries = new Querries();

                                    try {
                                        ResultSet resultSetRegister = dbQuerries.checkLoginAvailability(ActivityRegister.this);

                                        boolean isLoginAvailable = true;

                                        while (resultSetRegister.next()) {
                                            if (login.equals(resultSetRegister.getString("login"))) {
                                                runOnUiThread(() -> textViewLoginExists_register.setVisibility(View.VISIBLE));
                                                isLoginAvailable = false;
                                            }
                                        }

                                        if (isLoginAvailable) {

                                            //BCrypt hashing
                                            String hashedPassword = BCrypt.withDefaults().hashToString(12,
                                                    editTextPassword_register.getText().toString().toCharArray());

                                            dbQuerries.registerNewAccount(editTextLogin_register.getText().toString(),
                                                    hashedPassword, ActivityRegister.this);

                                            runOnUiThread(() -> {
                                                linearLayoutRegisterProgress.setVisibility(View.GONE); allowBackButton = true;
                                                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityRegister.this, R.style.AlertDialogStyleUpdate);
                                                alert.setTitle("Pomyślnie dodano nowe konto!");
                                                alert.setMessage("Możesz teraz się zalogować, aby odblokować możliwość zapisywania" +
                                                        " swoich danych do chmury.");
                                                alert.setPositiveButton("Ok", (dialogInterface, i) -> {
                                                    Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                                                    intent.putExtra("loginIntent", login);
                                                    startActivity(intent);
                                                    finish();
                                                });
                                                alert.setOnCancelListener(dialog -> {
                                                    Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                                                    intent.putExtra("loginIntent", login);
                                                    startActivity(intent);
                                                    finish();
                                                });
                                                alert.show();
                                            });
                                        }

                                    } catch (SQLException | NullPointerException | InterruptedException e) {
                                        runOnUiThread(() -> {
                                            linearLayoutRegisterProgress.setVisibility(View.GONE); allowBackButton = true;
                                            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        });
                                        Log.e("ActivityRegister 165", Log.getStackTraceString(e));
                                    }
                                    runOnUiThread(() -> {
                                        linearLayoutRegisterProgress.setVisibility(View.GONE); allowBackButton = true;
                                        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    });
                                } else {
                                    runOnUiThread(() -> {
                                        linearLayoutRegisterProgress.setVisibility(View.GONE);
                                        allowBackButton = true;
                                        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    });
                                }
                        } else {
                            runOnUiThread(() -> {
                                linearLayoutRegisterProgress.setVisibility(View.GONE); allowBackButton = true;
                                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityRegister.this, R.style.AlertDialogStyleUpdate);
                                alert.setTitle("Nie można się połączyć z serwerem");
                                alert.setMessage("Użyj innej sieci lub spróbuj ponownie później.");
                                alert.setPositiveButton("Rozumiem", (dialogInterface1, i1) -> Toast.makeText(ActivityRegister.this, "Brak połączenia z serwerem", Toast.LENGTH_SHORT).show());
                                alert.show();
                            });
                        }

                    } catch (ExecutionException | InterruptedException e) {
                        Log.e("ActivityRegister", Log.getStackTraceString(e));
                    }
                });
                thread.start();

            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityRegister.this, R.style.AlertDialogStyleUpdate);
                alert.setTitle("Brak połączenia z internetem");
                alert.setMessage("Włącz Wi-Fi lub transmisję danych.");
                alert.setPositiveButton("Rozumiem", (dialogInterface1, i1) ->
                    Toast.makeText(ActivityRegister.this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show());
                alert.show();
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
            runOnUiThread(() -> alert.alertLackOfData("Wszystkie pola muszą być uzupełnione.", context));

            flag = false;
        }

        if (login.length() < 4) {
            runOnUiThread(() -> textViewLoginTooShort_register.setVisibility(View.VISIBLE));

            flag = false;
        }

        if (!password.equals(passwordRepeat)) {
            runOnUiThread(() -> textViewPasswordsNotMatch_register.setVisibility(View.VISIBLE));

            flag = false;
        }

        if ((password.length() < 8) || (!letterPattern.matcher(password).find()) ||
                (!digitCasePatten.matcher(password).find())) {
            runOnUiThread(() -> textViewBadPassword_register.setVisibility(View.VISIBLE));

            flag = false;
        }

        return flag;
    }


    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    @Override
    public void onBackPressed() {
        if (allowBackButton) {
            Intent intent = new Intent(ActivityRegister.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
    }

}