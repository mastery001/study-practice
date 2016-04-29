package rmi.demo;

import java.rmi.Naming;
import java.util.List;

public class RMIClient {

	public static void main(String[] args) {
		try {
			// 调用远程对象，注意RMI路径与接口必须与服务器配置一致
			PersonService personService = (PersonService) Naming.lookup("rmi://127.0.0.1:6600/PersonService");
			List<PersonModel> personList = personService.getAll();
			for (PersonModel person : personList) {
				System.out.println("ID:" + person.getId() + " Age:" + person.getAge() + " Name:" + person.getName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
