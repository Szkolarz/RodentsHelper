package com.example.rodentshelper.SQL;

import android.os.StrictMode;

import com.example.rodentshelper.AsyncActivity;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;


public interface ConnectionSQL {

        default Connection connectToVPS() throws SQLException, InterruptedException {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            DriverManager.setLoginTimeout(3);

            System.out.println(DriverManager.getLoginTimeout() + "time");
            System.out.println(" dddd");
            try {

                final Connection con = DriverManager.getConnection("jdbc:mysql://mysql.mikr.us:3306/db_x266?", "x266", "489F_39b783");
                System.out.println(con + " connection");


                System.out.println(" gfdgfddgf");
                return con;
            } catch (Exception e) {
                System.out.println(" ciiiiiii");
            }
            System.out.println(" ggggg");
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
