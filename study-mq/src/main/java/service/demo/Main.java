package service.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:*applicationContext.xml");
		ac.getBean(MsgTransportService.class).producerAndConsumer();
	}
}
