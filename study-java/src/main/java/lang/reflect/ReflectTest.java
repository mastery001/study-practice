package lang.reflect;

import java.util.Arrays;

public class ReflectTest {
	
	public static void main(String[] args) {
		//�������
		int[] a1 = new int[]{1,2,3};
		int[] a2 = new int[4];
		int[][] a3 = new int[2][3];
		String[] a4 = new String[]{"111","222","333"};
		//�ж϶�Ӧ����������������ֶ��Ƿ����
		System.out.println(a1.getClass() == a2.getClass());
	/*	System.out.println(a1.getClass() == a3.getClass());
		System.out.println(a1.getClass() == a4.getClass());*/
		System.out.println(a1.getClass().getSuperclass().getName());
		
		System.out.println(a1);
		System.out.println(a4);
		System.out.println(Arrays.asList(a1));
		System.out.println(Arrays.asList(a4));
	}
}
