package com.example.rodentshelper.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//pusta klasa do korzystania z interfejsu
public class Querries implements ConnectionSQL{

    public ResultSet getVPSVersion() throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `Version`");
        return myres;
    }

    public ResultSet testSelect() throws SQLException, InterruptedException {
        {
            Statement stat = connectToVPS().createStatement();
            ResultSet myres = stat.executeQuery("select * from `test`");
            return myres;
        }
    }

    /*public ResultSet checkVersion() throws SQLException {
        {
            Statement stat = connectToVPS().createStatement();
            ResultSet myres = stat.executeQuery("SELECT * FROM `Version`");
            return myres;
        }
    }*/

    public ResultSet checkVersion(Integer id_animal) throws SQLException, InterruptedException {
        {
            Statement stat = connectToVPS().createStatement();
            ResultSet myres = stat.executeQuery("SELECT * FROM `Version` WHERE id_animal = " + id_animal);
            return myres;
        }
    }


    public ResultSet selectVersion() throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `Version`");
        return myres;

    }


    public ResultSet selectTreats(Integer id_animal) throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `Treats` WHERE id_animal = " + id_animal);
        return myres;

    }

    public ResultSet selectCageSupply(Integer id_animal) throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `CageSupply` WHERE id_animal = " + id_animal);
        return myres;
    }




}


