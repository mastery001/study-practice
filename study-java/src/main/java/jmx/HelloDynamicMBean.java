package jmx;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;

/**
 * DynamicMBean
 * 
 * @author zouziwen
 *
 *         2016年5月13日 下午12:04:37
 */
public class HelloDynamicMBean implements DynamicMBean {

	/**
	 * 2016年5月13日 下午12:06:13
	 */
	private final HelloService helloService;

	private MBeanInfo mBeanInfo;

	public HelloDynamicMBean(HelloService helloService) {
		this.helloService = helloService;
	}

	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException, ReflectionException {

		return null;
	}

	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {

	}

	@Override
	public AttributeList getAttributes(String[] attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		if (mBeanInfo == null) {
			try {
				Class<HelloService> cls = HelloService.class;
				// 用反射获得 "upTime" 属性的读方法
				Method readMethod = cls.getMethod("upTime", new Class[0]);
				// 用反射获得构造方法
				Constructor constructor = cls.getConstructor(new Class[] { HelloService.class });
				// 关于 "upTime" 属性的元信息 : 名称为 UpTime，只读属性 ( 没有写方法 )。
				MBeanAttributeInfo upTimeMBeanAttributeInfo = new MBeanAttributeInfo("UpTime",
						"The time span since server start", readMethod, null);
				// 关于构造函数的元信息
				MBeanConstructorInfo mBeanConstructorInfo = new MBeanConstructorInfo("Constructor for ServerMonitor",
						constructor);
				// ServerMonitor 的元信息，为了简单起见，在这个例子里，
				// 没有提供 invocation 以及 listener 方面的元信息
				mBeanInfo = new MBeanInfo(cls.getName(), "Monitor that controls the server",
						new MBeanAttributeInfo[] { upTimeMBeanAttributeInfo },
						new MBeanConstructorInfo[] { mBeanConstructorInfo }, null, null);
			} catch (Exception e) {
				throw new Error(e);
			}

		}
		return mBeanInfo;
	}

}
