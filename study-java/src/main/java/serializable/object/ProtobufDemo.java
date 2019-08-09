package serializable.object;

/**
 * Created by zouziwen on 2019/4/5.
 */
public class ProtobufDemo {

    public static void main(String[] args) {
        byte[] i32buf = writeVarint32(300);

        for(byte b : i32buf) {
            if(b != 0) {
                System.out.println(getBinString(b));
            }
        }
    }

    private static byte[] writeVarint32(int n) {
        byte[] i32buf = new byte[10];
        int idx = 0;
        while (true) {
            if ((n & ~0x7F) == 0) {
                i32buf[idx++] = (byte)n;
                break;
            } else {
                i32buf[idx++] = (byte)((n & 0x7F) | 0x80);
                // 步骤1：取出字节串末7位
                // 对于上述取出的7位：在最高位添加1构成一个字节
                // 如果是最后一次取出，则在最高位添加0构成1个字节

                n >>>= 7;
                // 步骤2：通过将字节串整体往右移7位，继续从字节串的末尾选取7位，直到取完为止。
            }
        }
        return i32buf;
    }

    private static String getBinString(byte b) {
        return String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');
    }
}
