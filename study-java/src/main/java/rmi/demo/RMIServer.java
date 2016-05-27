package rmi.demo;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
	
	public static void main(String[] args){
		try {
			PersonService service = new PersonServiceImpl();
			//注册通讯端口
			LocateRegistry.createRegistry(6600);
			//注册通讯路径
			Naming.rebind("rmi://127.0.0.1:6600/PersonService", service);
			System.out.println("Server start!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
