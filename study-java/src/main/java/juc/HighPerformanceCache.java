package juc;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**  
 *@Description:  基于ConcurrentMap的高性能的无锁缓存(此处描述不包含ConcurrentMap底层的分段锁)
 *@Author:zouziwen
 *@Since:2017年2月27日  
 *@Version:1.1.0  
 */
public class HighPerformanceCache<K,V> {
	
	private final ConcurrentMap<K , Future<V>> cache;
	
	private final Computable<K , V> computable;
	
	public HighPerformanceCache(Computable<K , V> computable) {
		cache = new ConcurrentHashMap<K , Future<V>>();
		this.computable = computable;
	}
	
	public V get(final K key) throws Throwable {
		while(true) {
			Future<V> f = cache.get(key);
			if(f == null) {
				FutureTask<V> ft = new FutureTask<V>(new Callable<V>() {

					@Override
					public V call() throws Exception {
						return computable.compute(key);
					}
				});
				f = cache.putIfAbsent(key, ft);
				if(f == null) {
					f = ft;
					ft.run();
				}
			}
			try {
				return f.get();
			}catch(CancellationException e) { 
				cache.remove(key);
			}catch (ExecutionException e) {
				throw e.getCause();
			} 
		}
	}

	public interface Computable<K , V> {
		
		V compute(K key) throws Exception;
	}
}
