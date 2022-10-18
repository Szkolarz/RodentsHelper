package com.example.rodentshelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rodentshelper.SQL.Querries;

import java.sql.Connection;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    TextView text1;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1=(TextView) findViewById(R.id.textView);
        button1=(Button) findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("aaaaa");

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {

                    Querries baza=new Querries();

                    ResultSet wynik=baza.testSelect();

                    while (wynik.next()){
                        text1.setText(wynik.getString("imie"));
                    }

                    System.out.println(baza.testSelect());


                    System.out.println("aaaaa");
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        });

    }
}