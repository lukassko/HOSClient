package hos.app.com.hosclient;

import android.app.Activity;
import android.os.Bundle;
import hos.app.com.hosclient.tcp.TCPClient;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TCPClient client  = new TCPClient();
        client.run();
    }
}
