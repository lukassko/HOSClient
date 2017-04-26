package com.app.hos.hosclient.tcp;

import com.app.hos.share.command.CommandBuilder;
import com.app.hos.share.command.HelloCommandBuilder;
import com.app.hos.share.command.builder.Command;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import com.app.hos.hosclient.tcp.ConnectionThread.SocketCreated;

public class TcpClient {

    private final String SERVER_IP = "10.0.2.2";
    private final int SERVER_PORT = 14020;
    private boolean runClient = true;
    private Socket socket;
    private CommandBuilder commandBuilder = new CommandBuilder();

    private ObjectInputStream objInput;
    private ObjectOutputStream objOutput;

    private TcpListener tcpListener;

    public TcpClient(TcpListener tcpListener) {
        this.tcpListener = tcpListener;
    }

    public synchronized void sendMessage(Command command) throws IOException {
        ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("SENDING command "+ command.getCommandType() + " serial: " + command.getSerialId());
        objOutput.writeObject(command);
        objOutput.flush();
    }

    public void stopClient() {
        runClient = false;
    }

    public void run(SocketCreated callback) {
        Socket socket = null;
        commandBuilder.setCommandBuilder(new HelloCommandBuilder());
        commandBuilder.createCommand();
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            this.socket = new Socket(serverAddr, SERVER_PORT);
            Command cmd = commandBuilder.getCommand();
            sendMessage(cmd);
            callback.onSocketCreated();
            while (runClient) {
                ObjectInputStream objInput = new ObjectInputStream(this.socket.getInputStream());
                Command command = null;
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
                    System.out.println("command received");
                    System.out.println(command.getCommandType());
                }
                command = null;
            }
        } catch(Exception e){
            System.out.println("Cannot connect to server");
            e.printStackTrace();
        } finally{
             if (socket != null)
                 try {
                     objOutput.close();
                     objInput.close();
                     socket.close();
                 } catch (IOException e) {
                        e.printStackTrace();
                 }
             System.out.println("Socket close");
        }
    }

    public void closeSocket() {
        try {
            objOutput.close();
            objInput.close();
            socket.close();
            System.out.println("Everything is close");
        } catch (IOException e) {
            System.out.println("Ecpetion during close");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Ecpetion during close 2");
            e.printStackTrace();
        }

    }
}
