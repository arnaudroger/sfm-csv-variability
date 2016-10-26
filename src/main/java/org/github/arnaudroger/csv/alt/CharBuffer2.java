package org.github.arnaudroger.csv;

import java.io.IOException;

public abstract class CharBuffer2 {

	protected char[] buffer;
	protected int bufferSize;
	protected int mark;

    public CharBuffer2(char[] buffer, final int bufferSize) {
		this.buffer = buffer;
		this.bufferSize = bufferSize;
	}

	public abstract int fillBuffer() throws IOException;
	public abstract int shiftBufferToMark() throws BufferOverflowException;

	public final char[] getCharBuffer() {
		return buffer;
	}
	public final int getBufferSize() {
		return bufferSize;
	}

}
