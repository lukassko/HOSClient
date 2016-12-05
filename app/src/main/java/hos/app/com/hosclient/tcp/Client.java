package hos.app.com.hosclient.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Łukasz on 05.12.2016.
 */
public class Client {

    private final String SERVER_IP = "192.168.254.104";
    private final int SERVER_PORT = 13020;
    private PrintWriter out;
    private BufferedReader in;

    public Client () {
    }

    public void runClient () {

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.write("Siema");
            out.flush();
        } catch (Exception e) {
            System.out.println("Cannot connect to server");
            e.printStackTrace();
        } finally {

        }
    }
}
