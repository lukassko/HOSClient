package hos.app.com.hosclient.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Łukasz on 05.12.2016.
 */
public class TcpClient {

    private String serverMessage;
    private final String SERVER_IP = "10.0.2.2";
    private final int SERVER_PORT = 14020;
    private boolean runClient = true;

    //private PrintWriter bufferOut;
    //private BufferedReader bufferIn;

    private ObjectInputStream objInput;
    private ObjectOutputStream objOutput;

    private TcpListener tcpListener;

    public TcpClient(TcpListener tcpListener) {
        this.tcpListener = tcpListener;
    }

    public void sendMessage(String message) throws IOException {
        if (objOutput != null) {
            objOutput.writeObject(new String(message));
            objOutput.flush();
        }
    }

    public void stopClient() {
        runClient = false;
    }

    public void run() {
        Socket socket = null;


        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            System.out.println("CREATEING SOCKET");
            socket = new Socket(serverAddr, SERVER_PORT);
            System.out.println("AFTER CREATEING SOCKET");


            objOutput = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("objOutput created");
            //objOutput.writeObject(new String("SIEMA"));
            objOutput.flush();
            System.out.println("Message sended to server");
            objInput = new ObjectInputStream(socket.getInputStream());
            System.out.println("objInput created");

            //bufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(runClient) {
                serverMessage = objInput.readObject().toString();
                if (serverMessage != null)
                    tcpListener.onMessageReceived(serverMessage);
                    System.out.println("SERVER MSG " + serverMessage);
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
