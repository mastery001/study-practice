package rmi.demo;

import java.io.Serializable;

public class PersonModel implements Serializable{

	/**
	 * 2016年4月28日 下午7:26:42
	 */
	private static final long serialVersionUID = -9085868290117812862L;
	
	private int id;
	private String name;
	private int age;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
