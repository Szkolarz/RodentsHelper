package com.example.rodentshelper.SQL;

import android.content.Context;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//pusta klasa do korzystania z interfejsu
public class Querries implements ConnectionSQL{

    public ResultSet getVPSVersion(Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `Version`");
        connectToVPS(context).close();
        return myres;
    }

    public ResultSet testSelect(Context context) throws SQLException, InterruptedException {
        {
            Statement stat = connectToVPS(context).createStatement();
            ResultSet myres = stat.executeQuery("select * from `test`");
            connectToVPS(context).close();
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

    public ResultSet checkVersion(Integer id_animal, Context context) throws SQLException, InterruptedException {
        {
            Statement stat = connectToVPS(context).createStatement();
            ResultSet myres = stat.executeQuery("SELECT * FROM `Version` WHERE id_animal = " + id_animal);
            connectToVPS(context).close();
            return myres;
        }
    }


    public ResultSet selectVersion(Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `Version`");
        connectToVPS(context).close();
        return myres;

    }


    public ResultSet selectGeneral(Integer id_animal, Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `General` WHERE id_animal = " + id_animal);
        connectToVPS(context).close();
        return myres;

    }

    public ResultSet selectDiseases(Integer id_animal, Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `Diseases` WHERE id_animal = " + id_animal);
        connectToVPS(context).close();
        return myres;

    }

    public ResultSet selectTreats(Integer id_animal, Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `Treats` WHERE id_animal = " + id_animal);
        connectToVPS(context).close();
        return myres;

    }

    public ResultSet selectCageSupply(Integer id_animal, Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `CageSupply` WHERE id_animal = " + id_animal);
        connectToVPS(context).close();
        return myres;
    }




    public void exportLocalDatabase(InputStream localData, Context context) throws SQLException, InterruptedException {
        Connection con = connectToVPS(context);
        String sql ="INSERT INTO LocalData (`login`, `file`) VALUES (?,?)";
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString (1, "ap");
        preparedStmt.setBinaryStream(2, localData);

        preparedStmt.execute();

        connectToVPS(context).close();
    }

    public ResultSet importLocalDatabase (String login, Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT file from `LocalData` WHERE login = '" + login + "'");
        connectToVPS(context).close();
        return myres;
    }

    public ResultSet checkLoginAvailability (Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT login from `Accounts`");
        connectToVPS(context).close();
        return myres;
    }

    public void registerNewAccount(String login, String password, Context context) throws SQLException, InterruptedException {
        Connection con = connectToVPS(context);
        String sql ="INSERT INTO Accounts (`login`, `password`) VALUES (?,?)";
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString (1, login);
        preparedStmt.setString(2, password);
        preparedStmt.execute();

        connectToVPS(context).close();
    }




}


