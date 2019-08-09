package dubbo.demo1.provider;

public class ProviderServiceImpl implements ProviderService{

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

}
