package serializable;

import com.caucho.hessian.io.HessianOutput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;
import serializable.object.Person;
import serializable.object.PersonExternalizable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SerializableTest {

	private void write(String fileName , Object obj) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
		} catch (Exception ex) {
			fail("Exception thrown during test: " + ex.toString());
		}
	}

	@Test
	public void serializeToDisk() {

		Person ted = new Person("Ted", "Neward", 39);
		List<Person> friends = new ArrayList<Person>();
		for(int i = 1 ; i < 1; i++) {
			friends.add(new Person("" + i, "" + i, 1));
		}
		ted.setFriends(friends);

		write("seri/serializable.txt" , ted);
	
	}

	@Test
	public void externalSerializeToDisk() {
		PersonExternalizable ted = new PersonExternalizable("Ted", "Neward", 39);
		List<PersonExternalizable> friends = new ArrayList<PersonExternalizable>();
		for(int i = 1 ; i < 1; i++) {
			friends.add(new PersonExternalizable("" + i, "" + i, 1));
		}
		ted.setFriends(friends);

		write("seri/externalizable.txt" , ted);

	}

	@Test
	public void kryoSerializeToDisk() throws FileNotFoundException {
		Person ted = new Person("Ted", "Neward", 39);
		List<Person> friends = new ArrayList<Person>();
		for(int i = 1 ; i < 1; i++) {
			friends.add(new Person("" + i, "" + i, 1));
		}
		ted.setFriends(friends);

		Kryo kryo = new Kryo();

		String fileName = "seri/kryo.txt";

		Output output = new Output(new FileOutputStream(fileName));

		kryo.writeObject(output, ted);
		//stream.flush();
		output.close();

//		KryoSerializer kryoSerializer = new KryoSerializer(Person.class);
//
//		write("seri/kryo.txt" , kryoSerializer.serialize(ted));

	}

	@Test
	public void moxySerializaToDisk() throws JAXBException, FileNotFoundException {
		Person ted = new Person("Ted", "Neward", 39);
		List<Person> friends = new ArrayList<Person>();
		for(int i = 1 ; i < 1; i++) {
			friends.add(new Person("" + i, "" + i, 1));
		}
		ted.setFriends(friends);


		JAXBContext context = JAXBContext.newInstance(Person.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("eclipselink.media-type", "application/json");
		String fileName = "seri/moxy.txt";

		OutputStream outputStream = new FileOutputStream(fileName);

		marshaller.marshal(ted, outputStream);
	}
	
	@Test
	public void serializeToObject() {
		try {
			String fileName = "seri/serializable.txt";

			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Person ted = (Person) ois.readObject();
			ois.close();

			assertEquals(ted.getFirstname(), "Ted");
//			assertEquals(ted.getSpouse().getFirstname(), "Charlotte");

			// Clean up the file
			new File(fileName).delete();
		} catch (Exception ex) {
			fail("Exception thrown during test: " + ex.toString());
		}
	}

	@Test
	public void hessionSerializeTest() throws IOException {
		HessianOutput hessianOutput = new HessianOutput(System.out);

		System.out.println(0x8000);

		StringBuilder builder = new StringBuilder();

		for(int i = 0 ; i < 0x8000 ; i ++) {
			builder.append(i);
		}

		hessianOutput.writeString(builder.toString());

//		hessianOutput.writeObject(new Person("Ted", "Neward", 39));
	}
}
