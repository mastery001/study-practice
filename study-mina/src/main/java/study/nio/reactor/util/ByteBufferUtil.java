package study.nio.reactor.util;

import java.nio.ByteBuffer;

public class ByteBufferUtil {

	private ByteBufferUtil(){}
	
	public static void clearIfPossible(Object message) {
		if (message instanceof ByteBuffer) {
			((ByteBuffer) message).clear();
		}
	}
	
}
