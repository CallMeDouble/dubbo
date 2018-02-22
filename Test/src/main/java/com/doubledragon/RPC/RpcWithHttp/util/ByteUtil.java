package com.doubledragon.RPC.RpcWithHttp.util;

/**
 * Created by zhushuanglong on 2018/1/11.
 */
public class ByteUtil {

    /**
     * @param i
     * @return
     */
    public static byte[] int2ByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static int bytes2Int(byte[] bytes) {
        int num = bytes[3] & 0xFF;
        num |= ((bytes[2] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF00);
        num |= ((bytes[2] << 24) & 0xFF00);
        return num;
    }
}