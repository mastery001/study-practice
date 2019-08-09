package rxjava;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by zouziwen on 2018/6/7.
 */
public class RxJavaSubscribeOn {

    //用指定的名称新建一个线程
    public static Scheduler getNamedScheduler(final String name) {
        return Schedulers.from(Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, name);
            }
        }));
    }

    //打印当前线程的名称
    public static void threadInfo(String caller) {
        System.out.println(caller + " => " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Observable.create(subscriber -> {
            threadInfo("OnSubscribe.call()");
            subscriber.onNext("RxJava");
        }).subscribeOn(getNamedScheduler("create之后的subscribeOn"))
                .doOnSubscribe(() -> threadInfo(".doOnSubscribe-1"))
                .subscribeOn(getNamedScheduler("doOnSubscribe之后的subscribeOn"))
                .doOnSubscribe(() -> threadInfo(".doOnSubscribe-2"))
                .subscribe(s -> {
                    threadInfo(".onNext()");
                    System.out.println(s + "-onNext");
                });

    }
}
