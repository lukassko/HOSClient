package com.app.hos.hosclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.app.hos.hosclient.tcp.ConnectionTask;
import com.app.hos.hosclient.tcp.TcpClient;
import com.app.hos.share.command.CommandBuilder;
import com.app.hos.share.command.HelloAbstractCommandBuilder;
import com.app.hos.share.command.StatusAbstractCommandBuilder;
import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.Command;
import hos.app.com.hosclient.R;

import java.io.IOException;

public class MainActivity extends Activity {

    // USE DESIGN ATTERN TO CRETAE/USE DIFFERENT COMMAND BUILDER (FACOTORY OR STRATEGY)
    private CommandBuilder commandBuilder = new CommandBuilder();
    private AbstractCommandBuilder statusAbstractCommandBuilder = new StatusAbstractCommandBuilder();

    private TcpClient tcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edit_text);
        Button send = (Button)findViewById(R.id.send_button);

        ConnectionTask.getInstance().execute();
        this.tcpClient = ConnectionTask.getInstance().getTcpClient();

        commandBuilder.setCommandBuilder(new HelloAbstractCommandBuilder());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                System.out.println("BUTTON CLICKED");
                commandBuilder.setCommandBuilder(new HelloAbstractCommandBuilder());
                commandBuilder.createCommand();
                Command cmd = commandBuilder.getCommand();
            if (tcpClient != null) {
                try {
                    tcpClient.sendMessage(cmd);
                } catch (IOException e) {
                    System.out.println("Command cannot be sent");
                    System.out.println(e);
                }

            } else {
                System.out.println("EMPTY TCP");
            }


                editText.setText("");
            }
        });
    }
}
