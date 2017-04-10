package com.app.hos.share.command;

import java.util.LinkedList;
import java.util.List;

import com.app.hos.hosclient.utility.Usage;
import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.result.Result;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.share.command.result.DeviceStatus.UsageType;

public class StatusAbstractCommandBuilder extends AbstractCommandBuilder {

    private Usage usage;
    @Override
    public void setCommandType() {
        String type = CommandType.MY_STATUS.toString();
        command.setCommandType(type);
    }

    @Override
    public void setResult() {
        List<Result> results = new LinkedList<Result>();
        results.add(getCpuUsage());
        results.add(getRamUsage());
        command.setResult(results);
    }

    private Result getCpuUsage() {
        double tmpUsage = usage.getInstance().getCpuUsage();
        System.out.println("CPU " + tmpUsage);
        return new DeviceStatus(UsageType.CPU, tmpUsage);
    }

    private Result getRamUsage() {
        double tmpUsage = usage.getInstance().getRamUsage();
        System.out.println("RAM " + tmpUsage);
        return new DeviceStatus(UsageType.RAM, tmpUsage);
    }
}
