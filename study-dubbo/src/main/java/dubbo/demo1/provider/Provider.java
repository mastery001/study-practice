package dubbo.demo1.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务提供者启动器
 * 
 * @author zouziwen
 *
 *         2016年4月25日 下午4:47:48
 */
public class Provider {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/dubbo/demo1/provider/provider.xml");
		context.start();

		System.in.read(); // 按任意键退出
	}
}
