package jmx;

public class HelloServiceImpl implements HelloService{

	private String name;
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
