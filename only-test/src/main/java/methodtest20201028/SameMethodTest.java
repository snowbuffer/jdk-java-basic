package methodtest20201028;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-10-28 10:04
 */
public class SameMethodTest {

    /**
     * 同名的方法只能在声明的类中进行反射
     */
    @Test
    public void test() throws Exception {
        Method test = A.class.getMethod("test");
        B b = new B();
        Object invoke = test.invoke(b);
        System.out.println(invoke);

        /**
         * Exception in thread "main" java.lang.IllegalArgumentException: object is not an instance of declaring class
         * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
         * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
         * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
         * 	at java.lang.reflect.Method.invoke(Method.java:498)
         * 	at methodtest20201028.SameMethodTest.main(SameMethodTest.java:16)
         */
    }
}
