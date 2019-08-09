package lang.reflect;

import java.lang.reflect.Field;

/** 
* @ClassName: FieldTest 

* @Description: TODO	������������

* @author lingfeng

* @date 2014-4-19 ����09:51:44 

*  
*/ 

public class FieldTest {		// �࿪ʼ
	
	public static void main(String[] args) throws Exception{	// main������ʼ
		/**
		 * TODO ȡ�ö����ж�Ӧ�ֶε�ֵ
		 */
		//���������ֶ������
		ReflectField pt = new ReflectField(3, 5);	
		//��ȡ�ֶ�y���ֽ���
		Field fieldY = pt.getClass().getField("y");
		//��ӡ��pt������y��ֵ
		System.out.println(fieldY.get(pt));
		//����Ѿ������Ķ����е�x
		/**
		 * ע����Ϊx��˽�еģ�����ֻ���ɴ˷�����ȡx���ֶ�
		 */
		Field fieldX = pt.getClass().getDeclaredField("x");
		//�����ֶ�x���Ա�ʹ��
		fieldX.setAccessible(true);
		//��ӡ��pt������x��ֵ
		System.out.println(fieldX.get(pt));
		
		/**
		 * TODO ȡ�ö����ж�Ӧ�ֶε�ֵ���滻
		 */
		ReplaceValue(pt);
		//����滻���pt�����е�ֵ���Զ�������д��toString����
		System.out.println(pt);
	}	// main��������

	
	/** 
	
	* @Title: ReplaceValue 
	
	* @Description: TODO 	ȡ�ö����ж�Ӧ�ֶε�ֵ���滻
	
	* @param @param pt   
	
	* @return void    �������� 
	
	* @throws 
	
	*/ 
	private static void ReplaceValue(Object obj) throws Exception{	// �����Ŀ�ʼ
		//��ȡ���������е��ֶ�
		Field[] fields = obj.getClass().getFields();
		//���ֶν��е���ѭ��
		for(Field field : fields) {			// for�Ŀ�ʼ
			//���ֶε�����ΪString����ʱ
			if(field.getType() == String.class) {	// if�Ŀ�ʼ
				//��ȡ�ֶε�ֵ
				String oldValue = (String)field.get(obj);
				//���ַ�����ֵ�����滻
				String newValue = oldValue.replace("b", "a");
				//���ֶε�ֵ�滻
				field.set(obj, newValue);
				
			}	// if����
		}	// for����
	}	// ��������
	
}	// �����
