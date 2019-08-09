package dubbo.demo1.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dubbo.demo1.provider.ProviderService;

public class Consumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/dubbo/demo1/consumer/consumer.xml");
		context.start();
		
		ProviderService provider = (ProviderService) context.getBean("providerService");
		String hello = provider.sayHello("World");
		
		System.out.println(hello);
	}
}
