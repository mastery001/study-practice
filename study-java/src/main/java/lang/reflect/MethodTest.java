package lang.reflect;

import java.lang.reflect.Method;

/** 
* @ClassName: MethodTest 

* @Description: TODO	������

* @author lingfeng

* @date 2014-4-20 ����09:26:53 

*  
*/ 

public class MethodTest {	// �࿪ʼ

	public static void main(String[] args) throws Exception {	// main������ʼ
		/**
		 *  Method�����ͨӦ��
		 */
		// ����String���Ͷ���str1������ֵ
		String str1 = "abc";
		// ���String������charAt�������ֶ�
		Method methodCharAt = str1.getClass().getMethod("charAt", int.class);
		// ͨ��Method���е�invoke���������ô��ֶ��д���ķ���
		System.out.println(methodCharAt.invoke(str1, 2));
		
		/**
		 *  Method��Ļ�ȡmain������Ӧ�ã���ȡ�������е�main����
		 */
		// ��ȡargs�����е�ֵ
		String startingClassName = args[0];
		// ��ȡmain�������ֶ�
		Method mainMethod = Class.forName(startingClassName).getMethod("main", String[].class);
		//����main����
		/**
		 *  ע��
		 *  ���⣺���յ����ݹ�ȥ��������������в���������������������Իᱨ��ָ���쳣
		 */
		// ���������1����
		mainMethod.invoke(null, (Object)new String[]{"111","asd","221"});
		// ���������2����
		mainMethod.invoke(null, new Object[]{new String[]{"111","asd","221"}});
	}	// main��������
	

}	// �����

/** 
* @ClassName: TestArguments 

* @Description: TODO	������һ��main��������

* @author lingfeng

* @date 2014-4-20 ����09:28:30 

*  
*/ 

class TestArguments {	// �࿪ʼ
	
	public static void main(String[] args) {	// main������ʼ
		//ѭ���������args�����е�ֵ
		for(String arg : args) {	// for�Ŀ�ʼ
			System.out.println(arg);
		}	// for����
	}	// main��������
}	// �����
