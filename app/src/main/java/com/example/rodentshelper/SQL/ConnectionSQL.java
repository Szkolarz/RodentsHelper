package com.example.rodentshelper.SQL;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.StrictMode;

import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.R;
import com.example.rodentshelper.Util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
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

                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

                encryptor.setPassword(context.getString(R.string.sp));

                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open("db.properties");

                Properties props = new EncryptableProperties(encryptor);
                props.load(inputStream);


                String datasourceUrl = props.getProperty("dbUrl");
                String datasourceLogin = props.getProperty("dbLogin");
                String datasourcePassword = props.getProperty("dbPassword");

                final Connection con = DriverManager.getConnection(datasourceUrl,
                        datasourceLogin, datasourcePassword);

                inputStream.close();
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
