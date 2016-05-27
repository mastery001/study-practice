package rmi.demo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PersonService extends Remote{
	
	public List<PersonModel> getAll() throws RemoteException;
}
