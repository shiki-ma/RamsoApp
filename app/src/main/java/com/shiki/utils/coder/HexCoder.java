package com.shiki.utils.coder;

import java.nio.charset.Charset;

/**
 * Created by Maik on 2016/3/1.
 */
public class HexCoder {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final Charset charset;

    public static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for (int j = 0; j < len; ++i) {
                int f = toDigit(data[j], j) << 4;
                ++j;
                f |= toDigit(data[j], j);
                ++j;
                out[i] = (byte) (f & 255);
            }

            return out;
        }
    }

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for (int j = 0; i < l; ++i) {
            out[j++] = toDigits[(240 & data[i]) >>> 4];
            out[j++] = toDigits[15 & data[i]];
        }

        return out;
    }

    public static String encodeHexString(byte[] data) {
        return new String(encodeHex(data));
    }

    protected static int toDigit(char ch, int index) throws RuntimeException {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        } else {
            return digit;
        }
    }

    public HexCoder() {
        this.charset = DEFAULT_CHARSET;
    }

    public HexCoder(Charset charset) {
        this.charset = charset;
    }

    public HexCoder(String charsetName) {
        this(Charset.forName(charsetName));
    }

    public byte[] decode(byte[] array) {
        return decodeHex((new String(array, this.getCharset())).toCharArray());
    }

    public Object decode(Object object) {
        try {
            char[] e = object instanceof String ? ((String) object).toCharArray() : (char[]) ((char[]) object);
            return decodeHex(e);
        } catch (ClassCastException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    public byte[] encode(byte[] array) {
        return encodeHexString(array).getBytes(this.getCharset());
    }

    public Object encode(Object object) {
        try {
            byte[] e = object instanceof String ? ((String) object).getBytes(this.getCharset()) : (byte[]) ((byte[]) object);
            return encodeHex(e);
        } catch (ClassCastException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getCharsetName() {
        return this.charset.name();
    }

    public String toString() {
        return super.toString() + "[charsetName=" + this.charset + "]";
    }
}
