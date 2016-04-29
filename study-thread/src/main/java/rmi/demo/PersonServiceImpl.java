package rmi.demo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class PersonServiceImpl extends UnicastRemoteObject implements PersonService{

	protected PersonServiceImpl() throws RemoteException {
		super();
	}

	/**
	 * 2016年4月28日 下午7:28:37
	 */
	private static final long serialVersionUID = -4260611620512528969L;

	@Override
	public List<PersonModel> getAll() throws RemoteException {
		System.out.println("Get Person Start!");
		List<PersonModel> personList=new LinkedList<PersonModel>();

		PersonModel person1=new PersonModel();
		person1.setAge(25);
		person1.setId(0);
		person1.setName("Leslie");
		personList.add(person1);

		PersonModel person2=new PersonModel();
		person2.setAge(25);
		person2.setId(1);
		person2.setName("Rose");
		personList.add(person2);

		return personList;
	}

}
