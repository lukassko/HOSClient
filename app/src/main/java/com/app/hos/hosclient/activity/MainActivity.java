package com.app.hos.hosclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.app.hos.hosclient.tcp.ConnectionThread;
import com.app.hos.hosclient.tcp.TcpClient;
import com.app.hos.hosclient.tcp.TcpListener;
import com.app.hos.hosclient.utility.Utility;
import com.app.hos.share.command.CommandBuilder;
import com.app.hos.share.command.HelloCommandBuilder;
import com.app.hos.share.command.StatusCommandBuilder;
import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.Command;
import hos.app.com.hosclient.R;

import java.io.IOException;

public class MainActivity extends Activity {

    // USE DESIGN ATTERN TO CRETAE/USE DIFFERENT COMMAND BUILDER (FACOTORY OR STRATEGY)
    private CommandBuilder commandBuilder = new CommandBuilder();
    private AbstractCommandBuilder statusAbstractCommandBuilder = new StatusCommandBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edit_text);
        Button send = (Button)findViewById(R.id.send_button);
        Button close = (Button)findViewById(R.id.close_button);

        Utility.setApplicationContext(getApplicationContext());

        final ConnectionThread connectionThread = new ConnectionThread(new TcpListener() {
            @Override
            public void onMessageReceived(Command command) {
            }
        });

        (new Thread(connectionThread)).start();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionThread.stopTcpConnection();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            commandBuilder.setCommandBuilder(new StatusCommandBuilder());
            commandBuilder.createCommand();
            Command cmd = commandBuilder.getCommand();
            connectionThread.sendCommand(cmd);
            }
        });
    }
}
