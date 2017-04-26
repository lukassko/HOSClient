package com.app.hos.hosclient.tcp;

import com.app.hos.share.command.CommandBuilder;
import com.app.hos.share.command.StatusCommandBuilder;
import com.app.hos.share.command.builder.Command;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ConnectionThread implements Runnable {

    public interface SocketCreated {
        public void onSocketCreated();
    }

    private TcpClient tcpClient = null;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Future<?> threadHandler;
    private CommandBuilder commandBuilder = new CommandBuilder();

    public ConnectionThread(TcpListener tcpListener) {
        tcpClient = new TcpClient(tcpListener);
    }

    public TcpClient getTcpClient() {
        return this.tcpClient;
    }

    @Override
    public void run() {
        tcpClient.run(new SocketCreated() {
            @Override
            public void onSocketCreated() {
                threadHandler = scheduler.scheduleAtFixedRate(sendStatusCommand, 10, 10, SECONDS);
            }
        });
    }

    public void stopTcpConnection() {
        if (threadHandler != null)
            threadHandler.cancel(true);
        if (tcpClient != null)
            tcpClient.closeSocket();
    }

    public void sendCommand(Command command) {
        try {
            tcpClient.sendMessage(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Runnable sendStatusCommand = new Runnable() {
        public void run() {
        commandBuilder.setCommandBuilder(new StatusCommandBuilder());
        commandBuilder.createCommand();
        Command cmd = commandBuilder.getCommand();
        try {
            tcpClient.sendMessage(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    };

}
