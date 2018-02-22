package com.doubledragon.RPC.RpcWithHttp.util;

/**
 * Created by zhushuanglong on 2018/1/11.
 */
public class Request {
    //协议编码
    private byte encode;
    //命令
    private String command;
    //命令长度
    private int commandLength;

    public byte getEncode() {
        return encode;
    }

    public void setEncode(byte encode) {
        this.encode = encode;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getCommandLength() {
        return commandLength;
    }

    public void setCommandLength(int commandLength) {
        this.commandLength = commandLength;
    }
}
