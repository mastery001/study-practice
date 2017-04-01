package introduction;


import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * Created by zouziwen on 2016/10/20.
 */
public class HelloWorld {

    public static void main(String args[]) throws InstantiationException, IllegalAccessException {
        System.out.println(new HelloWorld().say());

    }

    public String say() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        return dynamicType.newInstance().toString();
    }

}
