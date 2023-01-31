package com.gryzoniopedia.rodentshelper.SQL;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.StrictMode;

import com.example.rodentshelper.R;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public interface ConnectionSQL {

        default Connection connectToVPS(Context context) {
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
}
