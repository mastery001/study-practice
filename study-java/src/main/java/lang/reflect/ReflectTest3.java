package lang.reflect;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

/**
 * @ClassName: ReflectTest3
 * 
 * @Description: TODO ���Ի����ķ�������
 * 
 * @author lingfeng
 * 
 * @date 2014-4-24 ����10:56:54
 * 
 * 
 */

public class ReflectTest3 {

	public static void main(String[] args) {

		try {
			/**
			 * ע���˴�һ��Ҫ�������ĵ�·������������·������Ӳ���룬�������������
			 */
			// 1����ȡ�����ļ����׳��쳣FileNotFoundException
			//InputStream ips = new FileInputStream("config.properties");
			
			// 2�� ͨ����ȡ��������������ļ�
			//InputStream ips = ReflectTest3.class.getClassLoader().getResourceAsStream("com/onschool/reflect/config.properties");
			
			// 3�� �����ͬһ���������м�����Ŀ¼�µ��ļ�
			InputStream ips = ReflectTest3.class.getResourceAsStream("config.properties");
			// �������Զ���
			Properties prop = new Properties();
			
			// �������ļ��ж�������ֵ���������Զ���ΪHashtable��
			// �׳��쳣ΪIllegalArgumentException(�Ƿ������쳣)��IOException����������쳣��
			prop.load(ips);
			
			// �ر�ips���������ͷ������ϵͳ��������Դ
			ips.close();
			
			// �������ֵ�е�className����
			String className = prop.getProperty("className");
			
			// ����className�ಢʵ����
			// �׳������쳣InstantiationException(ʵ�����쳣)
			// IllegalAccessException(���ݴ洢�쳣)
			// ClassNotFoundException(��δ�ҵ����쳣)
			Collection collections = (Collection) Class.forName(className)
					.newInstance();
			
			// ��������ReflectField����
			ReflectField pt1 = new ReflectField(3, 3);
			ReflectField pt2 = new ReflectField(5, 5);
			ReflectField pt3 = new ReflectField(3, 3);

			// ��ReflectField������뵽������
			collections.add(pt1);
			collections.add(pt2);
			collections.add(pt3);
			collections.add(pt1);
			
			//��ӡ��collections�ĳ���
			System.out.println(collections.size());
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IllegalArgumentException e1) {
			// TODO: handle exception
		} catch (IOException e2) {
			// TODO: handle exception
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		}

	}

}
