package thread.common.demo.sync.queue;

import java.util.concurrent.BlockingQueue;

import threadsync.queue.BlockQueueByLockCondition;

public class BlockQueueBySynchronizedTest {

	public static void main(String[] args) {
		final BlockingQueue<Object> queue = new BlockQueueByLockCondition<Object>();
		executeFor(1, new ForCallable() {

			@Override
			public void call(int index) {
				new Thread(new Product(queue), "Product"+ (index+1)).start();
			}
		});

		executeFor(1, new ForCallable() {

			@Override
			public void call(int index) {
				new Thread(new Consumer(queue), "Consumer" + (index+1)).start();
			}
		});

	}

	private static void executeFor(int count, ForCallable callable) {
		for (int i = 0; i < count; i++) {
			callable.call(i);
		}
	}

	interface ForCallable {

		void call(int index);
	}
}

class Product implements Runnable {

	BlockingQueue<Object> queue;

	Product(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			try {
				System.out.println("put---" + i + "----" + Thread.currentThread().getName());
				queue.put(i);
				//TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable {

	BlockingQueue<Object> queue;

	Consumer(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Object result = queue.take();
				if(result == null) {
					break;
				}
				System.out.println("take--" + result+ "----" + Thread.currentThread().getName());
				//TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
