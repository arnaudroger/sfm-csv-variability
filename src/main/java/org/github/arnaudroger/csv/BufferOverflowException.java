package org.github.arnaudroger.csv;

import java.io.IOException;

public class BufferOverflowException extends IOException {
    public BufferOverflowException(String s) {
        super(s);
    }
}
