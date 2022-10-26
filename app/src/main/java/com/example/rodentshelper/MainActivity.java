package com.example.rodentshelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rodentshelper.SQL.Querries;

import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    //private SQLiteHelper dbHandler;
    TextView text1;
    TextView text2;
    Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // dbHandler = new SQLITEtest(MainActivity.this);

        Querries dbQuerries = new Querries();

        text1 = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);
        button1=(Button) findViewById(R.id.button);


        AsyncActivity internetAsyncCheck = new AsyncActivity();
        internetAsyncCheck.execute();

        System.out.println(internetAsyncCheck.getInternetConnectionInfo() + "qqqqqqqqqqqqq");




        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {
                    ResultSet resultSetTest = dbQuerries.testSelect();
                    ResultSet resultSetVersion = dbQuerries.checkVersion();

                    String a = "";

                    while (resultSetVersion.next()) {
                        a = (resultSetVersion.getString("number"));
                    }

                    Integer DBversion = Integer.valueOf(a);

                    Boolean internetCheck = internetAsyncCheck.getInternetConnectionInfo();

                    System.out.println("dsfdsfdsdfsfdsfds");
                    System.out.println(internetCheck + "dsfdsfdsdfsfdsfds");

                    if (internetCheck == true) {

                        SharedPreferences prefsFirstStart = getSharedPreferences("prefsFirstStart", MODE_PRIVATE);
                        boolean firstStart = prefsFirstStart.getBoolean("firstStart", true);

                        SharedPreferences prefsDB = getSharedPreferences("prefsDB", MODE_PRIVATE);
                        Integer sharedPreferencesDBVersion = prefsDB.getInt("dbversion", DBversion);
                        System.out.println(sharedPreferencesDBVersion + " xxxxx");
                        System.out.println(DBversion + " xxxxx");
                        System.out.println(firstStart + " xxxxx");

                        if (firstStart == true || !sharedPreferencesDBVersion.equals(DBversion)) {
                            System.out.println("CZEK @@@@@@@@@@@@@@@@");

                            SharedPreferences.Editor editorFirstStart = prefsFirstStart.edit();
                            editorFirstStart.putBoolean("firstStart", false);
                            editorFirstStart.apply();

                            SharedPreferences.Editor editorDB = prefsDB.edit();
                            editorDB.putInt("dbversion", DBversion);
                            editorDB.apply();
                        }

                        while (resultSetTest.next()){
                            text1.setText(resultSetTest.getString("imie"));
                            text2.setText(resultSetTest.getString("nazwisko"));
                            if (DBversion == 20221910) {

                            }
                            //dbHandler.addNewRodent(resultSetTest.getString("imie"), resultSetTest.getString("nazwisko"));
                        }
                    }
                    else {
                        System.out.println("Brak internetu");
                    }


                    System.out.println(dbQuerries.testSelect());


                }catch(Exception e){
                    System.out.println(e);
                }
            }
        });

    }

    public void addRodents(android.view.View view)
    {
        final Context context = this;
        Intent intent = new Intent(context, AddRodents.class);
        startActivity(intent);
    }


    public void viewVets(android.view.View view)
    {
        FlagSetup.setFlagVetAdd(0);
        final Context context = this;
        Intent intent = new Intent(context, ViewVets.class);
        startActivity(intent);
    }
}