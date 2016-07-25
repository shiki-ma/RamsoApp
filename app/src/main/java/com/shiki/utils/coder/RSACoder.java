package com.shiki.utils.coder;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Maik on 2016/3/1.
 */
public class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String CHIPER_ALGORITHM = "RSA/ECB/";
    public static final int KEY_SIZE = 1024;
    public static final byte[] PUBLIC_EXPONENT = new byte[]{(byte) 1, (byte) 0, (byte) 1};

    public RSACoder() {
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator e = KeyPairGenerator.getInstance("RSA");
            e.initialize(1024, new SecureRandom());
            KeyPair keyPair = e.genKeyPair();
            return keyPair;
        } catch (Exception var2) {
            throw new RuntimeException("Error when init key pair, errmsg: " + var2.getMessage(), var2);
        }
    }

    private static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        try {
            RSAPublicKeySpec e = new RSAPublicKeySpec(new BigInteger(1, modulus), new BigInteger(1, publicExponent));
            KeyFactory keyFac = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFac.generatePublic(e);
        } catch (Exception var4) {
            throw new RuntimeException("Error when generate rsaPubblicKey, errmsg: " + var4.getMessage(), var4);
        }
    }

    private static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        try {
            KeyFactory e = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(1, modulus), new BigInteger(1, privateExponent));
            return (RSAPrivateKey) e.generatePrivate(priKeySpec);
        } catch (Exception var4) {
            throw new RuntimeException("Error when generate rsaPrivateKey, errmsg: " + var4.getMessage(), var4);
        }
    }

    private static byte[] encrypt(Key key, byte[] data, RSACoder.PADDING padding) {
        try {
            Cipher e = Cipher.getInstance("RSA/ECB/" + (padding == null ? RSACoder.PADDING.NoPadding : padding));
            e.init(1, key);
            return e.doFinal(data);
        } catch (Exception var4) {
            throw new RuntimeException("Error when encrypt data, errmsg: " + var4.getMessage(), var4);
        }
    }

    public static byte[] encryptByPublicKey(byte[] publicKey, byte[] data, RSACoder.PADDING padding) {
        RSAPublicKey key = generateRSAPublicKey(publicKey, PUBLIC_EXPONENT);
        return encrypt(key, data, padding);
    }

    public static byte[] encryptByPrivateKey(byte[] publicKey, byte[] privateKey, byte[] data, RSACoder.PADDING padding) {
        RSAPrivateKey key = generateRSAPrivateKey(publicKey, privateKey);
        return encrypt(key, data, padding);
    }

    private static byte[] decrypt(Key key, byte[] data, RSACoder.PADDING padding) {
        try {
            Cipher e = Cipher.getInstance("RSA/ECB/" + (padding == null ? RSACoder.PADDING.NoPadding : padding));
            e.init(2, key);
            return e.doFinal(data);
        } catch (Exception var4) {
            throw new RuntimeException("Error when decrypt data, errmsg: " + var4.getMessage(), var4);
        }
    }

    public static byte[] decryptByPublicKey(byte[] publicKey, byte[] data, RSACoder.PADDING padding) {
        RSAPublicKey key = generateRSAPublicKey(publicKey, PUBLIC_EXPONENT);
        return decrypt(key, data, padding);
    }

    public static byte[] decryptByPrivateKey(byte[] publicKey, byte[] privateKey, byte[] data, RSACoder.PADDING padding) {
        RSAPrivateKey key = generateRSAPrivateKey(publicKey, privateKey);
        return decrypt(key, data, padding);
    }

    public static enum PADDING {
        NoPadding,
        PKCS1Padding;

        private PADDING() {
        }
    }
}
