package serializable;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import serializable.object.Person;

public class SimpleSerializable {

	@Test
	public void serializeToDisk() {
		try {
			Person ted = new Person("Ted", "Neward", 39);
			Person charl = new Person("Charlotte", "Neward", 38);

			ted.setSpouse(charl);
			charl.setSpouse(ted);

			FileOutputStream fos = new FileOutputStream("tempdata.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ted);
			oos.close();
		} catch (Exception ex) {
			fail("Exception thrown during test: " + ex.toString());
		}
	
	}
	
	@Test
	public void serializeToObject() {
		try {
			FileInputStream fis = new FileInputStream("tempdata.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Person ted = (Person) ois.readObject();
			ois.close();

			assertEquals(ted.getFirstname(), "Ted");
			assertEquals(ted.getSpouse().getFirstname(), "Charlotte");

			// Clean up the file
			new File("tempdata.ser").delete();
		} catch (Exception ex) {
			fail("Exception thrown during test: " + ex.toString());
		}
	}
}
