package jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class MBeanAgent {
	
	public static void main(String[] args) {
		//无法再jconsole使用的创建方式
//		MBeanServer server = MBeanServerFactory.createMBeanServer();
		//可在jconsole中使用的创建方式
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		try {
			HelloMBean mbean = new Hello(new HelloServiceImpl());
			ObjectName helloName = new ObjectName("MBeanTest:name=Hello");
			server.registerMBean(mbean, helloName);
			
			ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=4000");
			HtmlAdaptorServer  adapter = new HtmlAdaptorServer();
			server.registerMBean(adapter, adapterName);
			adapter.setPort(4000);
			
			adapter.start();
			System.out.println("start.....");
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
