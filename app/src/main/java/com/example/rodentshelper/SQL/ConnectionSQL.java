package com.example.rodentshelper.SQL;

import java.sql.*;


public interface ConnectionSQL {

        default Connection connectToVPS() throws SQLException {
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.mikr.us:3306/db_x266?useSSL=false&allowPublicKeyRetrieval=true", "x266", "489F_39b783");
            return con;
        }

}
