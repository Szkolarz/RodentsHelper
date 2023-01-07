package com.example.rodentshelper.SQL;

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

    public ResultSet getVPSVersion() throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `Version`");
        connectToVPS().close();
        return myres;
    }

    public ResultSet testSelect() throws SQLException, InterruptedException {
        {
            Statement stat = connectToVPS().createStatement();
            ResultSet myres = stat.executeQuery("select * from `test`");
            connectToVPS().close();
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
            connectToVPS().close();
            return myres;
        }
    }


    public ResultSet selectVersion() throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `Version`");
        connectToVPS().close();
        return myres;

    }


    public ResultSet selectGeneral(Integer id_animal) throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `General` WHERE id_animal = " + id_animal);
        connectToVPS().close();
        return myres;

    }

    public ResultSet selectDiseases(Integer id_animal) throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * FROM `Diseases` WHERE id_animal = " + id_animal);
        connectToVPS().close();
        return myres;

    }

    public ResultSet selectTreats(Integer id_animal) throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `Treats` WHERE id_animal = " + id_animal);
        connectToVPS().close();
        return myres;

    }

    public ResultSet selectCageSupply(Integer id_animal) throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT * from `CageSupply` WHERE id_animal = " + id_animal);
        connectToVPS().close();
        return myres;
    }




    public void exportLocalDatabase(InputStream localData) throws SQLException, InterruptedException {
        Connection con = connectToVPS();
        String sql ="INSERT INTO LocalData (`login`, `file`) VALUES (?,?)";
        PreparedStatement preparedStmt = con.prepareStatement(sql);
        preparedStmt.setString (1, "a");
        preparedStmt.setBinaryStream(2, localData);

        preparedStmt.execute();

        connectToVPS().close();
    }

    public ResultSet importLocalDatabase (String login) throws SQLException, InterruptedException {
        Statement stat = connectToVPS().createStatement();
        ResultSet myres = stat.executeQuery("SELECT 'file' from `LocalData` WHERE login = 'ak'");
        connectToVPS().close();
        return myres;
    }




}


