package com.shiki.utils.coder;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by Maik on 2016/3/1.
 */
public class DESCoder {
    public DESCoder() {
    }

    public static byte[] encrypt(byte[] data, String password) {
        try {
            SecureRandom e = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, securekey, e);
            return cipher.doFinal(data);
        } catch (Throwable var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] src, String password) {
        try {
            SecureRandom e = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, securekey, e);
            return cipher.doFinal(src);
        } catch (Throwable var7) {
            var7.printStackTrace();
            return null;
        }
    }
}
