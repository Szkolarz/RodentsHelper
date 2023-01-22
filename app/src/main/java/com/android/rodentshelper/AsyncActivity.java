package com.android.rodentshelper;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class AsyncActivity extends AsyncTask<Void, Boolean, Boolean> {


    private static Boolean internetConnectionInfo;

    public static Boolean getInternetConnectionInfo() {
        return internetConnectionInfo;
    }

    public void setInternetConnectionInfo(Boolean internetConnectionInfo) {
        AsyncActivity.internetConnectionInfo = internetConnectionInfo;
    }


    @Override
    protected Boolean doInBackground(Void... arg0) {
        try {
            int timeoutMs = 3000;
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

            socket.connect(socketAddress, timeoutMs);
            socket.close();
            setInternetConnectionInfo(true);
            System.out.println("true");
            return true;
        } catch (IOException e) {
            setInternetConnectionInfo(false);
            System.out.println("false");
            return false; }
    }




}
