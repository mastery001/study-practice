package nio;

import java.nio.ByteBuffer;

/**
 * Created by zouziwen on 2019/3/2.
 */
public class BufferDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(8);

//        buffer.limit(5);

        buffer.get();
        buffer.get();

        buffer.compact();

        buffer.slice();

        System.out.println(buffer);

//        buffer.mark();
//
//        buffer.get();
//        buffer.get();
//
//        buffer.reset();
//
//        buffer.flip();
//
//        buffer.rewind();
//
//        buffer.reset();
//
//
//        while(buffer.hasRemaining()) {
//            System.out.println(buffer.get());
//
//            System.out.println(buffer);
//        }

    }
}
