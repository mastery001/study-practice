package thread.common.flow;

/**
 * 通过join方法实现顺序执行方法
 * @author zouziwen
 *
 * 2016年4月14日 下午4:55:13
 */
public class SequenceByJoin {

	public static void main(String[] args) throws InterruptedException {
		SequenceByJoin s = new SequenceByJoin();
		Thread a = new Thread(s.new A());
		Thread b = new Thread(s.new B());
		Thread c = new Thread(s.new C());
		a.start();
		a.join();
		b.start();
		b.join();
		c.start();
	}
	
	class A implements Runnable {

		@Override
		public void run() {
			System.out.println(this.getClass().getSimpleName() + " start");
			for(int i = 0 ; i < 10 ; i ++ ) {
				System.out.println("A" + i);
			}
			System.out.println(this.getClass().getSimpleName() + " end");
		}
		
	}
	
	class B implements Runnable {

		@Override
		public void run() {
			System.out.println(this.getClass().getSimpleName() + " start");
			for(int i = 0 ; i < 10 ; i ++ ) {
				System.out.println("B" + i);
			}
			System.out.println(this.getClass().getSimpleName() + " end");
		}
		
	}
	
	class C implements Runnable {

		@Override
		public void run() {
			System.out.println(this.getClass().getSimpleName() + " start");
			for(int i = 0 ; i < 10 ; i ++ ) {
				System.out.println("C" + i);
			}
			System.out.println(this.getClass().getSimpleName() + " end");
		}
		
	}
}
