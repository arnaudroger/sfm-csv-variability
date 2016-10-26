package org.github.arnaudroger.csv.alt;

import org.github.arnaudroger.csv.orig.CharBuffer;

import java.io.IOException;

public final class CharSequenceCharBuffer2 extends CharBuffer2 {

	public CharSequenceCharBuffer2(final String str)
			throws IOException {
		super(str.toCharArray(), str.length());
	}

	public CharSequenceCharBuffer2(final CharSequence str)
			throws IOException {
		super(toCharArray(str), str.length());
	}

	@Override
	public final int fillBuffer() throws IOException {
		return -1;
	}

	@Override
	public final int shiftBufferToMark(int mark) {
		return 0;
	}

	private static char[] toCharArray(CharSequence charSequence) {
		if (charSequence instanceof String) {
			return ((String)charSequence).toCharArray();
		} else {
			char[] buffer = new char[charSequence.length()];for(int i = 0; i < buffer.length; i++) {
				buffer[i] = charSequence.charAt(i);
			}
			return buffer;
		}
	}
}
