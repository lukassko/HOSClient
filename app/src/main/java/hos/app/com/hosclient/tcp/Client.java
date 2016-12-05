package hos.app.com.hosclient.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Łukasz on 05.12.2016.
 */
public class Client {

    private final String SERVER_IP = "10.0.2.2";
    private final int SERVER_PORT = 13020;
    private PrintWriter out;
    private BufferedReader in;

    public Client () {
    }

    public void runClient () {
        Socket socket = null;
        System.out.println("Start CLIENT");
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            System.out.println("BEFORE");
            socket = new Socket(serverAddr, SERVER_PORT);
            System.out.println("AFTER");
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.write("Siema");
            System.out.println("Msg send");
            out.flush();
        } catch (Exception e) {
            System.out.println("Cannot connect to server");
            e.printStackTrace();
        }
        if (socket != null)
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("Socket close");
    }
}
