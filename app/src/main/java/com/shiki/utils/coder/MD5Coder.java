package com.shiki.utils.coder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Maik on 2016/3/1.
 */
public class MD5Coder {
    private static final String[] strDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5Coder() {
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (bByte < 0) {
            iRet = bByte + 256;
        }

        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        if (bByte < 0) {
            iRet = bByte + 256;
        }

        return String.valueOf(iRet);
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();

        for (int i = 0; i < bByte.length; ++i) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }

        return sBuffer.toString();
    }

    public static String getMD5Code(String source) {
        String resultString = null;

        try {
            new String(source);
            MessageDigest ex = MessageDigest.getInstance("MD5");
            resultString = byteToString(ex.digest(source.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return resultString;
    }
}
