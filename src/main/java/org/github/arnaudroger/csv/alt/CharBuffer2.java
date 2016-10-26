package org.github.arnaudroger.csv.alt;

import org.github.arnaudroger.csv.BufferOverflowException;

import java.io.IOException;

public abstract class CharBuffer2 {

	protected char[] buffer;
	protected int bufferSize;

    public CharBuffer2(char[] buffer, final int bufferSize) {
		this.buffer = buffer;
		this.bufferSize = bufferSize;
	}

	public abstract int fillBuffer() throws IOException;
	public abstract int shiftBufferToMark(int mark) throws BufferOverflowException;

	public final char[] getCharBuffer() {
		return buffer;
	}
	public final int getBufferSize() {
		return bufferSize;
	}

}
