package rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by zouziwen on 2018/6/6.
 */
public class RxJavaObservable {

    private static final Observable.OnSubscribe<String> on1 =  new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            System.out.println(1);
            // 当注释调用onNext操作时，不会输出数字2
//            subscriber.onNext("111");
            subscriber.onCompleted();
        }
    };

    public static void observer() {
        Observable observable = Observable.create(on1);

        observable = observable.lift(new Observable.Operator<String , String>() {
            @Override
            public Subscriber<? super String> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(2);
                        subscriber.onNext(s);
                    }
                };
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("complete");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };


        observable.subscribe(observer);
    }

    public static void main(String[] args) {
        observer();
    }

}
