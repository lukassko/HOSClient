package com.app.hos.hosclient.tcp;

import com.app.hos.share.command.builder.Command;

public interface TcpListener {
    public void onMessageReceived(Command command);
}
