package study.nio.reactor.filter;

import study.nio.reactor.FilterAdapter;
import study.nio.reactor.Session;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class MessageCodecFilter extends FilterAdapter {

	 private static final String ENCODER = MessageCodecFilter.class.getName()
	            + ".encoder";
	
	private final Charset charset;

	public MessageCodecFilter(Charset charset) {
		this.charset = charset;
	}

	@Override
	public void messageReceived(NextFilter nextFilter, Session session, Object message) throws Exception {
		if (!(message instanceof ByteBuffer)) {
			nextFilter.messageReceived(session, message);
			return;
		}
		String receiveText = decode(nextFilter, session, message);
		super.messageReceived(nextFilter, session, receiveText);
	}

	private String decode(NextFilter nextFilter, Session session, Object message) throws UnsupportedEncodingException {
		ByteBuffer in = (ByteBuffer) message;
		String receiveText = new String(in.array(), 0, in.capacity(), charset.name());
		int index = -1;
		if ((index = receiveText.lastIndexOf("\r\n")) != -1) {
			receiveText = receiveText.substring(0, index);
		}
		return receiveText;
	}

	
	@Override
	public void messageSent(NextFilter nextFilter, Session session, Object message) throws Exception {
		if (!(message instanceof ByteBuffer)) {
			nextFilter.messageSent(session, message);
			return;
		}
		super.messageSent(nextFilter, session, decode(nextFilter, session, message));
	}

	@Override
	public void filterWrite(NextFilter nextFilter, Session session, WriteRequest writeRequest) throws Exception {
		Object message = writeRequest.getMessage();
		if (message instanceof ByteBuffer) {
			nextFilter.filterWrite(session, writeRequest);
			return;
		}
		CharsetEncoder encoder = (CharsetEncoder) session.getAttribute(ENCODER);
		if (encoder == null) {
			encoder = charset.newEncoder();
			session.setAttribute(ENCODER, encoder);
		}
		String value = message.toString();

		ByteBuffer buf = ByteBuffer.allocate(value.length() * 2 + 1);
		buf.put(value.getBytes());
		buf.flip();
		nextFilter.filterWrite(session, new WriteRequest(buf, writeRequest.getDestination()));
	}

}
