package com.example.rodentshelper.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//pusta klasa do korzystania z interfejsu
public class Querries implements ConnectionSQL{

    public ResultSet getVPSVersion() throws SQLException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `Version`");
        return myres;
    }

    public ResultSet testSelect() throws SQLException {
        {
            Statement stat = connectToVPS().createStatement();
            ResultSet myres = stat.executeQuery("select * from `test`");
            return myres;
        }
    }

    public ResultSet checkVersion() throws SQLException {
        {
            Statement stat = connectToVPS().createStatement();
            ResultSet myres = stat.executeQuery("SELECT * FROM `Version`");
            return myres;
        }
    }




}


