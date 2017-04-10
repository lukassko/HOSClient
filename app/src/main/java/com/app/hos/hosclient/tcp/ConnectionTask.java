package com.app.hos.hosclient.tcp;

import android.os.AsyncTask;
import com.app.hos.share.command.builder.Command;

/**
 * Created by Łukasz on 05.12.2016.
 */
public class ConnectionTask extends AsyncTask<Command,Command,TcpClient> {

    private static ConnectionTask instance = null;
    private TcpClient tcpClient = null;

    public TcpClient getTcpClient() {
        return this.tcpClient;
    }

    protected ConnectionTask () {
        tcpClient = new TcpClient(new TcpListener() {
            @Override
            public void onMessageReceived(Command command) {
                publishProgress(command);
            }
        });
    }
    public static ConnectionTask getInstance() {
        if(instance == null) {
            instance = new ConnectionTask();
        }
        return instance;
    }

    @Override
    protected TcpClient doInBackground(Command... params) {

        tcpClient.run();

        return null;
    }

    @Override
    protected void onProgressUpdate(Command... command) {
        Command cmd = command[0];
        super.onProgressUpdate(cmd);
        System.out.println("RECEIVED COMMAND: " + cmd.getCommandType());
    }

}
