package com.github.chen.library;


public class ByteHelper {

    /**
     * 将byte [] 转 String
     *
     * @param data
     * @return
     */
    public static String byteToString(byte[] data) {
        if (data != null && data.length > 0) {
            String strText = String.format("%d bytes [", data.length);
            for (int i = 0; i < data.length; i++) {
                strText += String.format("%x ", data[i]);
            }
            strText += "]\n";
            return strText;
        }
        return "";
    }


    /**
     * 计算校验码
     */
    public static byte crc(byte[] buffer, int len) {
        byte result = buffer[0];
        for (int i = 1; i < len; i++) {
            result = (byte) (result ^ buffer[i]);
        }

        return result;
    }

    /**
     * 将byte转换成int数值
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        return b >= 0 ? b : b + 256;
    }

    /**
     * 将int数值转换为占1个字节的byte
     *
     * @param value
     * @return
     */
    public static byte intToByte(int value) {
        return (byte) (value & 0xFF);
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和byteToInt（）配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToByteArray(int value) {
        byte[] result = new byte[4];
        result[3] = (byte) ((value >> 24) & 0xFF);
        result[2] = (byte) ((value >> 16) & 0xFF);
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和byteToInt2（）配套使用
     *
     * @param value
     * @return
     */
    public static byte[] intToByteArray2(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) ((value >> 24) & 0xFF);
        result[1] = (byte) ((value >> 16) & 0xFF);
        result[2] = (byte) ((value >> 8) & 0xFF);
        result[3] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToByte（）配套使用
     *
     * @param bytes  byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int byteToInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF)
                | ((bytes[offset + 1] & 0xFF) << 8)
                | ((bytes[offset + 2] & 0xFF) << 16)
                | ((bytes[offset + 3] & 0xFF) << 24));
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToByte2（）配套使用
     * @param bytes
     * @param offset
     * @return
     */
    public static int byteToInt2(byte[] bytes, int offset) {
        return (((bytes[offset] & 0xFF) << 24)
                | ((bytes[offset + 1] & 0xFF) << 16)
                | ((bytes[offset + 2] & 0xFF) << 8)
                | (bytes[offset + 3] & 0xFF));
    }

}
