package hos.app.com.hosclient.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Łukasz on 05.12.2016.
 */
public class TCPClient {

    private String serverMessage;
    private final String SERVER_IP = "10.0.2.2";
    private final int SERVER_PORT = 14020;
    private boolean runClient = true;

    private PrintWriter bufferOut;
    private BufferedReader bufferIn;

    public TCPClient() {
    }

    private void sendMessage(String message) {
        if (bufferOut != null && bufferOut.checkError()) {
            bufferOut.write(message);
            bufferOut.flush();
        }
    }

    public void stopClient() {
        runClient = false;
    }

    public void run() {
        Socket socket = null;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVER_PORT);
            bufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            bufferOut.write("HELLO SPRING INTEGRATION!");
            System.out.println("Message sended to server");
            bufferOut.flush();

            bufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(runClient) {
                serverMessage = bufferIn.readLine();
                System.out.println(serverMessage);
                serverMessage = null;
            }
        } catch (Exception e) {
            System.out.println("Cannot connect to server");
            e.printStackTrace();
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            System.out.println("Socket close");
        }

    }
}
