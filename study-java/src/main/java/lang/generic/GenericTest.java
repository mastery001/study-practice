package lang.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/** 
* @ClassName: GenericTest 

* @Description: TODO	���͵��ۺ�ʵ��

* @author lingfeng

* @date 2014-4-24 ����09:00:16 

*  
*/ 

public class GenericTest {
	
	public static void main(String[] args) {
		/**ʵ����HashMap����ָ������ K-->String V-->Integer */
		/**Create a HashMap Instance and specified generic  */
		Map<String,Integer> maps = new HashMap<String,Integer>();
		
		/**��ָ����ֵ���ӳ���е�ָ��������*/
		/**Associates the specified value and the specified key in this map*/
		maps.put("yzw", 21);
		maps.put("zwf", 21);
		maps.put("zzw", 20);
		
		/**���maps�����Set��ͼ���Ӷ����Զ�Set���е���*/
		/**get a Set view from the maps object,in order to iterator on the Set*/
		Set<Map.Entry<String,Integer>> entrySet = maps.entrySet();
		
		//��entrySet������е���
		for(Map.Entry<String, Integer> entry : entrySet) {
			//���maps�еļ�ֵ��(Key-Value pair)
			System.out.println(entry.getKey() + "��" + entry.getValue());
		}
		//���Է�����Ĳ���������
		genericClassTest();
		
		Object[] arrays = {1,2,3,4};
		Collection<Object> collection = new ArrayList<Object>();
		copy(collection, arrays);
		System.out.println(collection);
		
		swap(new String[]{"a","b","c"},1,2);
		//swap(new int[]{1,2,3,4},2,3);
	}
	
	/** ��ͨ�����ʹ��
	 * 	�����ڴ��ڣ�ͨ����Ķ�����ʹ����Ҫ�������͵ķ���
	 */
	private static Object compareToCollection(Collection<?> collection) {
		//collection.add(new String("abc"));	����ȡ
		return null;
	}
	
	/**
	 * ע: ������b����Ľ��Ϊtrue   ��ôΪʲô�أ�
	 * ��ΪVector<T>�е�T�����÷�Χֻ�ڱ����ڼ䣬һ�����������java���Լ��������(erasure)
	 * �����������ڼ������������ֽ�����Ȼû�б�,���������ڼ䲻���ڷ��͵Ľṹ
	 */
	private static void genericClassTest() {
		Vector<String> v1 = new Vector<String>();
		Vector<Integer> v2 = new Vector<Integer>();
		boolean b = v1.getClass()== v2.getClass();
		System.out.println(b);
	}
	
	private static <T> void copy(Collection<T> collection , T[] arrays){
		for(T array : arrays) {
			collection.add(array);
		}
	}
	
	/**
	 *  ע���˷������ڽ�������������λ�õ�ֵ
	 */
	private static <T>void swap(T[] array , int i , int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
