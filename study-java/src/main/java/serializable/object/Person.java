package serializable.object;

import java.io.Serializable;

public class Person implements Serializable{
	
	/**
	 * 2016年5月1日 下午1:48:15
	 */
	private static final long serialVersionUID = -5938793522690145860L;
	private String firstname;
	private String lastname;
	private int age;
	private Person spouse;
	
	public Person(String firstname, String lastname, int age) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public int getAge() {
		return age;
	}
	
	public Person getSpouse() {
		return spouse;
	}
	public void setSpouse(Person spouse) {
		this.spouse = spouse;
	}
	@Override
	public String toString() {
		return "Person [firstname=" + firstname + ", lastname=" + lastname + ", age=" + age + ", spouse=" + spouse
				+ "]";
	}
	
}
