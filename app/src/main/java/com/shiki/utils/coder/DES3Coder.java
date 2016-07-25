package com.shiki.utils.coder;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Maik on 2016/3/1.
 */
public class DES3Coder {
    private static final String Algorithm = "DESede";

    public DES3Coder() {
    }

    public static byte[] encryptMode(byte[] src, String password) {
        try {
            SecretKeySpec e3 = new SecretKeySpec(build3DesKey(password), "DESede");
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(1, e3);
            return c1.doFinal(src);
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        } catch (NoSuchPaddingException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return null;
    }

    public static byte[] decryptMode(byte[] src, String password) {
        try {
            SecretKeySpec e3 = new SecretKeySpec(build3DesKey(password), "DESede");
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(2, e3);
            return c1.doFinal(src);
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        } catch (NoSuchPaddingException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return null;
    }

    private static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }

        return key;
    }
}
