package com.gryzoniopedia.rodentshelper.SQL;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//pusta klasa do korzystania z interfejsu
public class Querries implements ConnectionSQL{

    public ResultSet getVPSVersion(Context context) throws SQLException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `Version`");
        connectToVPS(context).close();
        return myres;
    }

    public ResultSet testSelect(Context context) throws SQLException {
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

    public ResultSet checkVersion(Integer id_animal, Context context) throws SQLException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT * FROM `Version` WHERE id_animal = " + id_animal);
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }


    public ResultSet selectVersion(Context context) throws SQLException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT * from `Version`");
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }


    public ResultSet selectGeneral(Integer id_animal, Context context) throws SQLException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT * FROM `General` WHERE id_animal = " + id_animal);
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return myres;

    }

    public ResultSet selectDiseases(Integer id_animal, Context context) throws SQLException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT * FROM `Diseases` WHERE id_animal = " + id_animal);
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }

    public ResultSet selectTreats(Integer id_animal, Context context) throws SQLException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT * from `Treats` WHERE id_animal = " + id_animal);
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }

    public ResultSet selectCageSupply(Integer id_animal, Context context) throws SQLException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT * from `CageSupply` WHERE id_animal = " + id_animal);
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }


    public void deleteBeforeExport(String login, Context context) throws SQLException {
        Connection con = connectToVPS(context);
        String sql ="DELETE FROM LocalData WHERE login = (?)";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setString (1, login);
            preparedStmt.execute();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void exportLocalDatabase(InputStream localData, Context context) {
        Connection con = connectToVPS(context);
        String sql ="INSERT INTO LocalData (`login`, `file`) VALUES (?,?)";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            SharedPreferences prefsLoginName = context.getSharedPreferences("prefsLoginName", Context.MODE_PRIVATE);
            preparedStmt.setString (1, prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"));
            preparedStmt.setBinaryStream(2, localData);
            preparedStmt.execute();
            connectToVPS(context).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ResultSet importLocalDatabase (String login, Context context) throws SQLException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT file from `LocalData` WHERE login = '" + login + "'");
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }

    public void setExportDate(String login, Date date, Context context) throws SQLException {
        Connection con = connectToVPS(context);
        String sql ="UPDATE Accounts SET `export_date` = (?) WHERE `login` = (?)";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setDate(1, date);
            preparedStmt.setString(2, login);
            preparedStmt.execute();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getExportDate (String login, Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT export_date from `Accounts` WHERE login = '" + login + "'");
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }

    public ResultSet checkLoginAvailability (Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT login from `Accounts`");
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }

    public ResultSet checkLoginAndPassword (Context context) throws SQLException, InterruptedException {
        Statement stat = connectToVPS(context).createStatement();
        ResultSet myres = null;
        try {
            myres = stat.executeQuery("SELECT login, password from `Accounts`");
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return myres;
    }

    public void registerNewAccount(String login, String password, Context context) throws SQLException, InterruptedException {
        Connection con = connectToVPS(context);
        String sql ="INSERT INTO Accounts (`login`, `password`) VALUES (?,?)";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setString (1, login);
            preparedStmt.setString(2, password);
            preparedStmt.execute();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}