package com.app.hos.share.command;

import com.app.hos.hosclient.utility.Usage;
import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.result.Result;
import com.app.hos.share.command.result.DeviceStatus;

public class StatusCommandBuilder extends AbstractCommandBuilder {

    private Usage usage;

    @Override
    public void setCommandType() {
        String type = CommandType.MY_STATUS.toString();
        command.setCommandType(type);
    }

    @Override
    public void setResult() {
        command.setResult(getResult());
    }

    private Result getResult() {
        double cpuUsage = usage.getInstance().getCpuUsage();
        double ramUsage = usage.getInstance().getRamUsage();
        return new DeviceStatus(ramUsage, cpuUsage);
    }

}
