package com.app.hos.hosclient.tcp;

import com.app.hos.share.command.CommandBuilder;
import com.app.hos.share.command.HelloAbstractCommandBuilder;
import com.app.hos.share.command.StatusAbstractCommandBuilder;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.result.DeviceStatus;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {

    private final String SERVER_IP = "10.0.2.2";
    private final int SERVER_PORT = 14020;
    private boolean runClient = true;
    private Socket socket; // TEST
    private CommandBuilder commandBuilder = new CommandBuilder();

    private ObjectInputStream objInput;
    private ObjectOutputStream objOutput;

    private TcpListener tcpListener;

    public TcpClient(TcpListener tcpListener) {
        this.tcpListener = tcpListener;
    }

    public void sendMessage(Command command) throws IOException {
        if (objOutput != null) {
            System.out.println("SENDING command "+ command.getCommandType());
            objOutput.writeObject(command);
            objOutput.flush();
            System.out.println("NEW COMMAND SENT");
           // objOutput.reset();
        }

    }

    public void stopClient() {
        runClient = false;
    }

    public void run() {
        Socket socket = null;

        commandBuilder.setCommandBuilder(new StatusAbstractCommandBuilder());
        commandBuilder.createCommand();

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            System.out.println("CREATEING SOCKET");
            socket = new Socket(serverAddr, SERVER_PORT);
            this.socket = socket;
            objOutput = new ObjectOutputStream(socket.getOutputStream());
            Command cmd = commandBuilder.getCommand();
            sendMessage(cmd);
            objInput = new ObjectInputStream(socket.getInputStream());
            System.out.println("objInput created");
            Command command = null;
            while (runClient) {
                try {
                    command = (Command) objInput.readObject();
                } catch (ClassNotFoundException e) {
                    System.out.println("TEST1");
                    System.out.println(e.toString());
                } catch (InvalidClassException e) {
                    System.out.println("TEST2");
                    System.out.println(e.toString());
                } catch (Exception e) {
                    System.out.println("TEST3");
                    System.out.println(e.toString());

                }
                if (command != null) {
                        System.out.println("TEST0");
                        if (command != null) {
                            System.out.println("command received");
                            System.out.println(command.getCommandType());
                           //sendMessage(cmd);
                            //tcpListener.onMessageReceived(command);
                        } else {
                            System.out.println("command is null");
                        }

                }
                command = null;
            }
            } catch(Exception e){
                System.out.println("Cannot connect to server");
                e.printStackTrace();
            } finally{
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
