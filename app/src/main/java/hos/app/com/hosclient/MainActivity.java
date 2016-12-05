package hos.app.com.hosclient;

import android.app.Activity;
import android.os.Bundle;
import hos.app.com.hosclient.tcp.Client;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Client client  = new Client();
        client.runClient();
    }
}
