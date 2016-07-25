package com.shiki.utils.io.stream;

import java.io.InputStream;

/**
 * Created by Maik on 2016/3/1.
 */
public class ClosedInputStream extends InputStream {
    public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

    public ClosedInputStream() {
    }

    public int read() {
        return -1;
    }
}
