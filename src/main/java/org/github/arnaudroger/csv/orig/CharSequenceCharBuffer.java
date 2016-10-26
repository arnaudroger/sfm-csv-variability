package org.github.arnaudroger.csv.orig;

import java.io.IOException;

public final class CharSequenceCharBuffer extends CharBuffer {

	public CharSequenceCharBuffer(final String str)
			throws IOException {
		super(str.toCharArray(), str.length());
	}

	public CharSequenceCharBuffer(final CharSequence str)
			throws IOException {
		super(toCharArray(str), str.length());
	}

	@Override
	public final int fillBuffer() throws IOException {
		return -1;
	}

	@Override
	public final int shiftBufferToMark() {
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
