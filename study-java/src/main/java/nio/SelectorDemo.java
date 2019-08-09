package nio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zouziwen on 2019/2/28.
 */
public class SelectorDemo {

    public static void main(String[] args) throws IOException {
        // create a Selector
        Selector selector = Selector.open();

        // new Server Channel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // config async
        ssc.configureBlocking(false);

        ssc.socket().bind(new InetSocketAddress(8080));

        // register to selector
        // Notes：这里只能注册OP_ACCEPT事件，否则将会抛出IllegalArgumentException,详见AbstractSelectableChannel#register方法
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // loop
        for (; ; ) {
            int nKeys = selector.select();

            if (nKeys > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();

                for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();

                    // 处理客户端连接事件
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

                        SocketChannel clientChannel = serverSocketChannel.accept();

                        clientChannel.configureBlocking(false);

                        clientChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024 * 1024));

                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        ByteBuffer buf = (ByteBuffer) key.attachment();

                        int readBytes = 0;
                        int ret = 0;

                        try {
                            while ((ret = socketChannel.read(buf)) > 0) {
                                readBytes += ret;
                            }

                            if (readBytes > 0) {
                                String message = decode(buf);
                                System.out.println(message);

                                // 这里注册写事件，因为写事件基本都处于就绪状态；
                                // 从处理逻辑来看，一般接收到客户端读事件时也会伴随着写，类似HttpServletRequest和HttpServletResponse
                                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);

                            }
                        } finally {
                            // 将缓冲区切换为待读取状态
                            buf.flip();
                        }

                    } else if (key.isValid() && key.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        ByteBuffer buf = (ByteBuffer) key.attachment();

                        if (buf.hasRemaining()) {
                            socketChannel.write(buf);
                        } else {
                            // 取消写事件，否则写事件内的代码会不断执行
                            // 因为写事件就绪的条件是判断缓冲区是否有空闲空间，绝大多时候缓存区都是有空闲空间的
                            key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
                        }

                        // 丢弃本次内容
                        buf.compact();
                    }

                    // 注意, 在每次迭代时, 我们都调用 "it.remove()" 将这个 key 从迭代器中删除,
                    // 因为 select() 方法仅仅是简单地将就绪的 IO 操作放到 selectedKeys 集合中,
                    // 因此如果我们从 selectedKeys 获取到一个 key, 但是没有将它删除, 那么下一次 select 时, 这个 key 所对应的 IO 事件还在 selectedKeys 中.
                    it.remove();
                }
            }

        }
    }

    /**
     * 将ByteBuffer转换为String
     *
     * @param in
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String decode(ByteBuffer in) throws UnsupportedEncodingException {
        String receiveText = new String(in.array(), 0, in.capacity(), Charset.defaultCharset());
        int index = -1;
        if ((index = receiveText.lastIndexOf("\r\n")) != -1) {
            receiveText = receiveText.substring(0, index);
        }
        return receiveText;
    }

}
