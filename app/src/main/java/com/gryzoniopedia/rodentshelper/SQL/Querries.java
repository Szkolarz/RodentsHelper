package com.gryzoniopedia.rodentshelper.SQL;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * FROM `Version` WHERE id_animal = ?");
            preparedStatement.setInt(1, id_animal);
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("checkVersion", Log.getStackTraceString(e));
        }
        return myres;
    }


    public ResultSet selectVersion(Context context) throws SQLException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * from `Version`");
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("Select Version", Log.getStackTraceString(e));
        }
        return myres;
    }

    public ResultSet selectVersionById(Integer id_animal, Context context) throws SQLException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * from `Version` WHERE id_animal = ?");
            preparedStatement.setInt(1, id_animal);
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("Select Version Where ID ", Log.getStackTraceString(e));
        }
        return myres;
    }


    public ResultSet selectGeneral(Integer id_animal, Context context) throws SQLException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * FROM `General` WHERE id_animal = ?");
            preparedStatement.setInt(1, id_animal);
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("Select General", Log.getStackTraceString(e));
        }

        return myres;

    }

    public ResultSet selectDiseases(Integer id_animal, Context context) throws SQLException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * FROM `Diseases` WHERE id_animal = ?");
            preparedStatement.setInt(1, id_animal);
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("selectDiseases", Log.getStackTraceString(e));
        }
        return myres;
    }

    public ResultSet selectTreats(Integer id_animal, Context context) throws SQLException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * from `Treats` WHERE id_animal = ?");
            preparedStatement.setInt(1, id_animal);
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("selectTreats", Log.getStackTraceString(e));
        }
        return myres;
    }

    public ResultSet selectCageSupply(Integer id_animal, Context context) throws SQLException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * from `CageSupply` WHERE id_animal = ?");
            preparedStatement.setInt(1, id_animal);
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("selectCageSupply", Log.getStackTraceString(e));
        }
        return myres;
    }


    public void deleteBeforeExport(String login, Context context) throws SQLException {
        try {
            Connection con = connectToVPS(context);
            String sql ="DELETE FROM LocalData WHERE login = (?)";

            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setString (1, login);
            preparedStmt.execute();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("deleteBeforeExport", Log.getStackTraceString(e));
        }
    }

    public void exportLocalDatabase(InputStream localData, Context context) {
        try {
            Connection con = connectToVPS(context);
            String sql ="INSERT INTO LocalData (`login`, `file`) VALUES (?,?)";

            PreparedStatement preparedStmt = con.prepareStatement(sql);
            SharedPreferences prefsLoginName = context.getSharedPreferences("prefsLoginName", Context.MODE_PRIVATE);
            preparedStmt.setString (1, prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"));
            preparedStmt.setBinaryStream(2, localData);
            preparedStmt.execute();
            connectToVPS(context).close();
        } catch (Exception e) {
            Log.e("exportLocalDatabase", Log.getStackTraceString(e));
        }
    }


    public ResultSet importLocalDatabase (String login, Context context) throws SQLException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT file from `LocalData` WHERE login = ?");
            preparedStatement.setString (1, login);
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("importLocalDatabase", Log.getStackTraceString(e));
        }
        return myres;
    }

    public void setExportDate(String login, Date date, Context context) throws SQLException {
        try {
            Connection con = connectToVPS(context);
            String sql ="UPDATE Accounts SET `export_date` = (?) WHERE `login` = (?)";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setDate(1, date);
            preparedStmt.setString(2, login);
            preparedStmt.execute();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("setExportDate", Log.getStackTraceString(e));
        }
    }
    public ResultSet getExportDate (String login, Context context) throws SQLException, InterruptedException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT export_date from `Accounts` WHERE login = ?");
            preparedStatement.setString (1, login);
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("getExportDate", Log.getStackTraceString(e));
        }
        return myres;
    }

    public ResultSet checkLoginAvailability (Context context) throws SQLException, InterruptedException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT login from `Accounts`");
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("checkLoginAvailability", Log.getStackTraceString(e));
        }
        return myres;
    }

    public ResultSet checkLoginAndPassword (Context context) throws SQLException, InterruptedException {
        ResultSet myres = null;
        try {
            Connection con = connectToVPS(context);
            PreparedStatement preparedStatement = con.prepareStatement("SELECT login, password from `Accounts`");
            myres = preparedStatement.executeQuery();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("checkLoginAndPassword", Log.getStackTraceString(e));
        }
        return myres;
    }

    public void registerNewAccount(String login, String password, Context context) throws SQLException, InterruptedException {
        try {
            Connection con = connectToVPS(context);
            String sql ="INSERT INTO Accounts (`login`, `password`) VALUES (?,?)";

            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setString (1, login);
            preparedStmt.setString(2, password);
            preparedStmt.execute();
            connectToVPS(context).close();
        } catch (NullPointerException e) {
            Log.e("registerNewAccount", Log.getStackTraceString(e));
        }
    }
}