package com.app.hos.hosclient.tcp;

import android.os.AsyncTask;

/**
 * Created by Łukasz on 05.12.2016.
 */
public class ConnectionTask extends AsyncTask<String,String,TcpClient> {
    private static ConnectionTask instance = null;
    private TcpClient tcpClient = null;

    public TcpClient getTcpClient() {
        return this.tcpClient;
    }

    public static ConnectionTask getInstance() {
        if(instance == null) {
            instance = new ConnectionTask();
        }
        return instance;
    }

    @Override
    protected TcpClient doInBackground(String... strings) {
        tcpClient = new TcpClient(new TcpListener() {

            @Override
            public void onMessageReceived(String message) {
                publishProgress(message);
            }
        });

        tcpClient.run();

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        String receivedValue = values[0];
        System.out.println("receivedValue: " + receivedValue);
    }

}
