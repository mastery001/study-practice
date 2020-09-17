package lang.introspector;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;

import lang.reflect.ReflectField;

/** 
* @ClassName: IntroSpectorTest 

* @Description: TODO		��ʡ�Ĳ���

* @author lingfeng

* @date 2014-4-24 ����02:48:36 

*  
*/ 

public class IntroSpectorTest{
	
	public static void main(String[] args) throws Exception {
		//����һ���µ�ReflectField����rf1
		ReflectField rf1 = new ReflectField(3, 4);
		
		//����һ���ַ���propertyNameֵΪx
		String propertyName = "x";
		//
		PropertyDescriptor pd = new PropertyDescriptor(propertyName,rf1.getClass());
		Method methodX = pd.getReadMethod();
		System.out.println(methodX.invoke(rf1));
		
		//���߰�BeanUtils�Ļ���ʹ��
		System.out.println(BeanUtils.getProperty(rf1, propertyName));
		//��javabean�е�������ֵ
		BeanUtils.setProperty(rf1, propertyName, "9");
		System.out.println(BeanUtils.getProperty(rf1, propertyName));
	}
}
