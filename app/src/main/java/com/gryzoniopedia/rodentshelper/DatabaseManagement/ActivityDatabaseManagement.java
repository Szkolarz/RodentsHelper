package com.gryzoniopedia.rodentshelper.DatabaseManagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.gryzoniopedia.rodentshelper.Notifications.NotificationsActivity;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;
import com.gryzoniopedia.rodentshelper.AsyncActivity;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAO;
import com.gryzoniopedia.rodentshelper.ROOM.DateFormat;
import com.gryzoniopedia.rodentshelper.ROOM.Visits.ViewVisits;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ActivityDatabaseManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Import/eksport danych");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView textViewLoginName_export_import, textViewExportDate_export_import, textViewImportDate_export_import;
        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other, imageViewLogOff;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);


        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ActivityDatabaseManagement.this, ViewRodents.class);
            startActivity(intent);
            finish();
        });


        Button buttonImport = findViewById(R.id.buttonImport);
        Button buttonExport = findViewById(R.id.buttonExport);
        Button buttonLogin_importExport = findViewById(R.id.buttonLogin_importExport);
        Button buttonRegister_importExport = findViewById(R.id.buttonRegister_importExport);

        LinearLayout linearLayout_notLogged = findViewById(R.id.linearLayout_notLogged);
        LinearLayout linearLayout_logged = findViewById(R.id.linearLayout_logged);


        SharedPreferences prefsCloudSave = getApplicationContext().getSharedPreferences("prefsCloudSave", Context.MODE_PRIVATE);

        //if user is logged on
        if (prefsCloudSave.getBoolean("prefsCloudSave", false)) {
            linearLayout_logged.setVisibility(View.VISIBLE);

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO dao = db.dao();

            List<CloudAccountModel> cloudAccountModel;
            cloudAccountModel = dao.getAllCloudAccountData();
            db.close();

            imageViewLogOff = findViewById(R.id.imageViewLogOff);
            textViewLoginName_export_import = findViewById(R.id.textViewLoginName_export_import);
            textViewExportDate_export_import = findViewById(R.id.textViewExportDate_export_import);
            textViewImportDate_export_import = findViewById(R.id.textViewImportDate_export_import);
            SharedPreferences prefsLoginName = getApplicationContext().getSharedPreferences("prefsLoginName", Context.MODE_PRIVATE);
            textViewLoginName_export_import.setText(prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"));


            for (int i=0; i<cloudAccountModel.size(); i++) {
                if (prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy").equals(cloudAccountModel.get(i).getLogin())) {
                    try {
                        textViewExportDate_export_import.setText(DateFormat.formatDate(cloudAccountModel.get(i).getExport_date()));
                    } catch (NullPointerException e){
                        System.out.println("Empty export; error: " + e);
                    }
                    try {
                        textViewImportDate_export_import.setText(DateFormat.formatDate(cloudAccountModel.get(i).getImport_date()));
                    } catch (NullPointerException e){
                        System.out.println("Empty import; error: " + e);
                    }
                }
            }

            buttonExport.setOnClickListener(view -> {

                AlertDialog.Builder alertCloud = new AlertDialog.Builder(ActivityDatabaseManagement.this, R.style.AlertDialogStyle);
                alertCloud.setTitle("Zapisywanie danych do chmury");
                alertCloud.setMessage("Czy na pewno chcesz zapisać swoje dane na chmurze?\n\n" +
                        "Jeśli używałeś(aś) tej opcji już wcześniej, twoje starsze dane zostaną skasowane i zastąpione" +
                        " aktualnymi; jeśli więc posiadasz już jakieś zapisane dane na chmurze, zostaną one nadpisane.");

                alertCloud.setPositiveButton("Tak", (dialogInterfaceCloud, j) -> {

                    if (isNetworkConnected(ActivityDatabaseManagement.this)) {

                        Thread threadProgressBar = new Thread(() -> {
                            Intent intent = new Intent(ActivityDatabaseManagement.this, ActivityProgressBar.class);
                            intent.putExtra("content", "Zapisywanie danych na serwer...");
                            startActivityForResult(intent, 1);
                        });
                        threadProgressBar.start();

                        Thread thread = new Thread(() -> {
                            try {
                                if (new AsyncActivity().execute().get()) {
                                    File dbMain = getDatabasePath("rodents_helper");
                                    File dbShm = getDatabasePath("rodents_helper-shm");
                                    File dbWal = getDatabasePath("rodents_helper-wal");

                                    ExportAndImport.exportDatabase(dbMain, dbShm, dbWal, prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"), ActivityDatabaseManagement.this);
                                    finishActivity(1);
                                    runOnUiThread(() -> {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityDatabaseManagement.this, R.style.AlertDialogStyleUpdate);
                                        alert.setTitle("Pomyślnie zapisano dane do chmury!");
                                        alert.setMessage("Wszystkie twoje dane zostały pomyślnie zapisane w internetowej chmurze.\n\n" +
                                                "Możesz je od teraz załadować na dowolnym urządzeniu w dowolnym momencie po zalogowaniu " +
                                                "się na twoje konto.");
                                        alert.setPositiveButton("Ok", (dialogInterface, i) -> {
                                            Toast.makeText(ActivityDatabaseManagement.this, "Pomyślnie zapisano dane do chmury", Toast.LENGTH_SHORT).show();
                                            reloadActivity();
                                        });
                                        alert.setOnCancelListener(dialog -> {
                                            Toast.makeText(ActivityDatabaseManagement.this, "Pomyślnie zapisano dane do chmury", Toast.LENGTH_SHORT).show();
                                            reloadActivity();
                                        });
                                        alert.show();
                                    });
                                } else {
                                    finishActivity(1);
                                    runOnUiThread(this::serverErrorAlert);
                                }
                            } catch (ExecutionException | InterruptedException e) {
                                finishActivity(1);
                                Log.e("164 ActivityDatabaseManagement", Log.getStackTraceString(e));
                            }
                        });
                        thread.start();

                    } else {
                        noInternetAlert();
                    }

                    });

                alertCloud.setNegativeButton("Nie", (dialogInterfaceCloud, j) -> Toast.makeText(ActivityDatabaseManagement.this, "Anulowano", Toast.LENGTH_SHORT).show());
                alertCloud.create().show();

            });


            buttonImport.setOnClickListener(view -> {

                String whenExportDate;
                if (textViewExportDate_export_import.getText().toString().equals("Brak")) {
                    whenExportDate = "<nie zapisywano jeszcze danych>";
                } else {
                    whenExportDate = textViewExportDate_export_import.getText().toString();
                }



                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityDatabaseManagement.this, R.style.AlertDialogStyle);
                alert.setTitle("Wczytywanie danych z chmury");
                alert.setMessage("Czy na pewno chcesz zaimportować dane z chmury?\n\n" +
                        "Wszystkie aktualne dane znajdujące się w pamięci aplikacji zostaną usunięte i będą " +
                        "zastąpione pobranymi danymi z chmury (ostatni zapis do chmury wykonano: " +
                        whenExportDate + ").");

                alert.setPositiveButton("Tak", (dialogInterface, i) -> {


                    if (isNetworkConnected(ActivityDatabaseManagement.this)) {

                        Thread threadProgressBar = new Thread(() -> {
                                Intent intent = new Intent(ActivityDatabaseManagement.this, ActivityProgressBar.class);
                                intent.putExtra("content", "Wczytywanie danych z bazy...");
                                startActivityForResult(intent, 1);
                        });
                        threadProgressBar.start();


                        Thread thread = new Thread(() -> {
                            try {
                                if (new AsyncActivity().execute().get()) {
                                    String s = getDatabasePath("rodents_helper").toString();
                                    String s1 = s.substring(0,s.lastIndexOf("/") + 1);
                                    s1.trim();

                                    Date dateGet = Calendar.getInstance().getTime();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    String formattedDate = df.format(dateGet);

                                        try {
                                            boolean haveDataImported = ExportAndImport.importDatabase(ActivityDatabaseManagement.this,
                                                    prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"));

                                            if (haveDataImported) {

                                                AppDatabase db1 = Room.databaseBuilder(getApplicationContext(),
                                                        AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                                                dao.updateCloudAccountImportDate((java.sql.Date.valueOf(formattedDate)), prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"));
                                                db1.close();

                                                finishActivity(1);
                                                runOnUiThread(() -> {
                                                    AlertDialog.Builder alert1 = new AlertDialog.Builder(ActivityDatabaseManagement.this, R.style.AlertDialogStyleUpdate);
                                                    alert1.setTitle("Pomyślnie wczytano dane z chmury!");
                                                    alert1.setMessage("Wszystkie twoje dane zapisane w dniu " + textViewExportDate_export_import.getText().toString() +
                                                            " zostały pomyślnie załadowane do aplikacji." );
                                                    alert1.setPositiveButton("Ok", (dialogInterface1, i1) -> {
                                                        Toast.makeText(ActivityDatabaseManagement.this, "Pomyślnie załadowano dane z chmury", Toast.LENGTH_SHORT).show();
                                                        reloadActivity();
                                                    });
                                                    alert1.setOnCancelListener(dialog -> {
                                                        Toast.makeText(ActivityDatabaseManagement.this, "Pomyślnie załadowano dane z chmury", Toast.LENGTH_SHORT).show();
                                                        reloadActivity();
                                                    });
                                                    alert1.show();
                                                });
                                            } else {

                                                finishActivity(1);
                                                runOnUiThread(() -> {
                                                    AlertDialog.Builder alert1 = new AlertDialog.Builder(ActivityDatabaseManagement.this, R.style.AlertDialogStyleUpdate);
                                                    alert1.setTitle("Brak zapisu w chmurze");
                                                    alert1.setMessage("W twojej prywatnej chmurze nie znajdują się jeszcze żadne dane.\n\n" +
                                                            "Możesz je dodać za pomocą przycisku 'Zapisz dane w chmurze'.");
                                                    alert1.setPositiveButton("Ok", (dialogInterface1, i1) -> {
                                                        Toast.makeText(ActivityDatabaseManagement.this, "Brak zapisu w chmurze", Toast.LENGTH_SHORT).show();
                                                        reloadActivity();
                                                    });
                                                    alert1.setOnCancelListener(dialog -> {
                                                        Toast.makeText(ActivityDatabaseManagement.this, "Brak zapisu w chmurze", Toast.LENGTH_SHORT).show();
                                                        reloadActivity();
                                                    });
                                                    alert1.show();
                                                });
                                            }

                                        } catch (IOException | SQLException | InterruptedException e) {
                                            finishActivity(1);
                                            Log.e("266 ActivityDatabaseManagement", Log.getStackTraceString(e));
                                        }

                                } else {
                                    finishActivity(1);
                                    runOnUiThread(this::serverErrorAlert);
                                }
                            } catch (ExecutionException | InterruptedException e) {
                                Log.e("274 ActivityDatabaseManagement", Log.getStackTraceString(e));
                            }
                        });

                        thread.start();

                    } else {
                        noInternetAlert();
                    }

                });
                alert.setNegativeButton("Nie", (dialogInterface, i) -> Toast.makeText(ActivityDatabaseManagement.this, "Anulowano", Toast.LENGTH_SHORT).show());

                alert.create().show();


            });


            imageViewLogOff.setOnClickListener(view -> {
                SharedPreferences.Editor prefsEditorCloudSave = prefsCloudSave.edit();
                prefsEditorCloudSave.putBoolean("prefsCloudSave", false);
                prefsEditorCloudSave.apply();

                reloadActivity();
            });



        } else {
            linearLayout_notLogged.setVisibility(View.VISIBLE);

            buttonLogin_importExport.setOnClickListener(view -> {
                startActivity(new Intent(ActivityDatabaseManagement.this, ActivityLogin.class));
                finish();
            });

            buttonRegister_importExport.setOnClickListener(view -> {
                startActivity(new Intent(ActivityDatabaseManagement.this, ActivityRegister.class));
                finish();
            });
        }



        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

    }


    private void reloadActivity() {
        startActivity(new Intent(ActivityDatabaseManagement.this, ActivityDatabaseManagement.class));
        finish();
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void noInternetAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityDatabaseManagement.this, R.style.AlertDialogStyleUpdate);
        alert.setTitle("Brak połączenia z internetem");
        alert.setMessage("Włącz Wi-Fi lub transmisję danych.");
        alert.setPositiveButton("Rozumiem", (dialogInterface1, i1) -> Toast.makeText(ActivityDatabaseManagement.this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show());
        alert.show();
    }

    private void serverErrorAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityDatabaseManagement.this, R.style.AlertDialogStyleUpdate);
        alert.setTitle("Nie można się połączyć z serwerem");
        alert.setMessage("Użyj innej sieci lub spróbuj ponownie później.");
        alert.setPositiveButton("Rozumiem", (dialogInterface1, i1) -> Toast.makeText(ActivityDatabaseManagement.this, "Brak połączenia z serwerem", Toast.LENGTH_SHORT).show());
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ActivityDatabaseManagement.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}