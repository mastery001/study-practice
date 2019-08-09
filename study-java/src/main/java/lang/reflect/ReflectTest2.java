package lang.reflect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/** 
* @ClassName: ReflectTest2 

* @Description: TODO	����ArrayList��HashSet������

* @author lingfeng

* @date 2014-4-21 ����08:16:40 

*  
*/ 

public class ReflectTest2 {
	
	public static void main(String[] args) {
		/**
		 *  ��new����ArrayList����ʱ�򼯺ϼ���n�����󳤶���Ϊn
		 */
		//���弯���࣬new����ArrayList����
		//Collection collections = new ArrayList();
		/**
		 *  ��new����HashSet����ʱ�򼯺ϼ������ʱ�Ȼ�Ѱ���Ƿ������ͬ����
		 *  ��Ϊ��ͬ�����򲻼��뼯���У�ʹ��֮ǰ�Ķ���
		 *  ��ͨ������Ĳ�����дhashCode����ʱ����������ͬ������Ϊ��ͬһ����
		 */
		//���弯���࣬new����HashSet����
		Collection collections = new HashSet();
		//��������ReflectField����
		ReflectField pt1 = new ReflectField(3, 3);	
		ReflectField pt2 = new ReflectField(5, 5);	
		ReflectField pt3 = new ReflectField(3, 3);	
		//��ReflectField������뵽������
		collections.add(pt1);
		collections.add(pt2);
		collections.add(pt3);
		collections.add(pt1);
		
		/**
		 * �ڴ�й©���ԣ�������뵽�����в��ܸı�����ֵ����Ȼ�޷�ɾ���˶���
		 */
		//pt1.y = 7;
		//collections.remove(pt1);
		System.out.println(collections.size());
		
	
	}
}
