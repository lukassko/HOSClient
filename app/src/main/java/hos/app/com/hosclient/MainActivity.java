package hos.app.com.hosclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import hos.app.com.hosclient.tcp.ConnectionTask;
import hos.app.com.hosclient.tcp.TcpClient;

import java.io.IOException;

public class MainActivity extends Activity {

    private TcpClient tcpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edit_text);
        Button send = (Button)findViewById(R.id.send_button);

        ConnectionTask.getInstance().execute();
        this.tcpClient = ConnectionTask.getInstance().getTcpClient();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();

                //sends the message to the server
                if (tcpClient != null) {
                    try {
                        tcpClient.sendMessage(message);
                    } catch (IOException e) {

                    }

                }
                editText.setText("");
            }
        });
    }
}
