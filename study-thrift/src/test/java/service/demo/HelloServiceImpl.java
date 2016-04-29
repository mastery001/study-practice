package service.demo;

import org.apache.thrift.TException;

public class HelloServiceImpl implements Hello.Iface {

	@Override
	public String helloString(String para) throws TException {
		System.out.println("call the helloString method , and param is " + para);
		return para;
	}

	@Override
	public int helloInt(int para) throws TException {
		System.out.println("call the helloInt method , and param is " + para);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return para;
	}

	@Override
	public boolean helloBoolean(boolean para) throws TException {
		System.out.println("call the helloBoolean method , and param is " + para);
		return para;
	}

	@Override
	public void helloVoid() throws TException {
		System.out.println("Hello World");
	}

	@Override
	public String helloNull() throws TException {
		return "null";
	}

}
