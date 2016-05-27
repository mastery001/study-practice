package jmx;

import java.util.Objects;

public class Hello implements HelloMBean {

	private final HelloService service;

	public Hello(HelloService service) {
		Objects.requireNonNull(service);
		this.service = service;
	}

	@Override
	public String status() {
		return "this is a Controller MBean,name is " + service.getName();
	}

	@Override
	public void setName(String name) {
		 service.setName(name);
	}

	@Override
	public String getName() {
		return  service.getName();
	}

}
