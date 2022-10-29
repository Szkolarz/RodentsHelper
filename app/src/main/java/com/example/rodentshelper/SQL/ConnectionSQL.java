package com.example.rodentshelper.SQL;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public interface ConnectionSQL {

        default Connection connectToVPS() throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.mikr.us:3306/db_x266?useSSL=false&allowPublicKeyRetrieval=true", "x266", "489F_39b783");
            return con;
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
