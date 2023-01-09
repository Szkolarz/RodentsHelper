package com.example.rodentshelper.SQL;

import android.content.Context;
import android.os.StrictMode;

import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;


public interface ConnectionSQL {

        default Connection connectToVPS(Context context) throws SQLException, InterruptedException {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            DriverManager.setLoginTimeout(3);

            try {


                final Connection con = DriverManager.getConnection(Util.getProperty("dbUrl",context),
                        Util.getProperty("dbLogin",context), Util.getProperty("dbPassword",context));

                return con;
            } catch (Exception e) {
                System.out.println(e+ " Error");
            }
            return null;

        }


    default boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("connected");

        } catch (Exception e) {
            return false;
        }
    }

}
