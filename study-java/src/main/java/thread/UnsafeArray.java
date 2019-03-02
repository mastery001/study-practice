package thread;

import sun.misc.Unsafe;

/**
 * Created by zouziwen on 2019/1/24.
 */
public class UnsafeArray {

    static int[] arrs;

    private static final Unsafe unsafe;

    static {
        unsafe = UnsafeUtils.get();

        Class<?> clazz = UnsafeArray[].class;

        int baseOffset = unsafe.arrayBaseOffset(clazz);

        int indexScale = unsafe.arrayIndexScale(clazz);

        if ((indexScale & indexScale - 1) != 0)
            System.out.println(1111);

        System.out.println(baseOffset + "-" + indexScale);

        System.out.println(Integer.toBinaryString(indexScale));
        System.out.println(Integer.numberOfLeadingZeros(indexScale));
        System.out.println(Integer.numberOfTrailingZeros(indexScale));
    }

    public static void main(String[] args) {

        System.out.println("-------------------------");

        System.out.println(Integer.toBinaryString(19));
        System.out.println(Integer.toBinaryString(19 << 1));
        System.out.println(Integer.toBinaryString(19 >> 1));
    }


}
