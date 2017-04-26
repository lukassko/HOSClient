package com.app.hos.share.command.builder;

import com.app.hos.hosclient.utility.Utility;
import com.app.hos.hosclient.utility.exceptions.NullFieldException;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class AbstractCommandBuilder {

    protected Command command;

    public Command getCommand() {
        return this.command;
    }

    public void createCommand() {
        String androidId;
        try {
            androidId = Utility.getAndroidId();
        } catch (NullFieldException e) {
            androidId = "Unknown";
        }
        this.command = new Command();
        command.setSerialId(androidId);
    }

    public abstract void setCommandType ();

    public abstract void setResult ();

}
