package com.wangwenjun.concurrent.classloader.chapter7;

import com.wangwenjun.concurrent.classloader.chapter3.MyClassLoader;
import com.wangwenjun.concurrent.classloader.chapter5.SimpleClassLoader;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-29 01:20
 */
public class ClassLoaderTest7 {

    public static void main(String[] args) throws Exception {
//        test1();

//        test2();

        test3();
    }

    /**
     * 维持双亲委派机制
     */
    public static void test3() throws Exception {
        System.out.println("MyClassLoader.loadClass前：CustomException => " + RuntimeException.class.getClassLoader());

        MyClassLoader classLoader = new MyClassLoader("MyClassLoader");
        classLoader.setDir("/Users/snowbuffer/Documents/dev/workspaces-me/jdk-java-basic/thread-classloader/third_classes");
        /**
         * MyClassLoader 在loadClass => CustomException extends RuntimeException
         *   会委托给父类进行查找，RuntimeException，由于父类是BootClassLoader, 能找到对应的类，因此BootClassLoader加载这个类
         *
         *   紧接着子类开始查找CustomException，还是会先委托给父类loader，但是父类loader找不到，最终由子类完成查找，因此 CustomException的加载器是子类
         *
         *   总结：
         *      子类加载器可以看到父类的加载器加载的类  继承关系  每次查找都是从下晚上
         *      运行时包主要体现在强制类型转换上
         *
         *      DriverManager 打破双亲委派机制，因为双亲委派是从下往上找，bootClassLoader为最低七点，于是利用了ServiceLoader来打破传统的规则，详见DriverManager.loadInitialDrivers方法，利用子类的加载器找到类
         *      参考：https://blog.csdn.net/mofeizhi/article/details/106727203
         *
         */
        Class<?> aClass = classLoader.loadClass("com.wangwenjun.concurrent.classloader.chapter7.CustomException");
        System.out.println("MyClassLoader.loadClass: " + aClass);
        System.out.println("MyClassLoader.loadClass, CustomException.classLoader =>  " + aClass.getClassLoader());

        System.out.println("==");

        // 这里为什么没有报错，因此RuntimeException的加载器两个，优先取的是子类的加载器，由于CustomException的加载器也是子类，因此运行时不会报错
        RuntimeException myObjectInterface = (RuntimeException) aClass.newInstance(); // 能直接引用
        System.out.println("newInstance.class.classLoader => " + myObjectInterface.getClass().getClassLoader());
        System.out.println("MyClassLoader.loadClass后：CustomException.classLoader => " + RuntimeException.class.getClassLoader());
        System.out.println("SimpleClassLoader.loadClass, CustomException.superClass =>  " + myObjectInterface.getClass().getSuperclass());
        System.out.println("SimpleClassLoader.loadClass, CustomException.superClass.classLoader =>  " + myObjectInterface.getClass().getSuperclass().getClassLoader());

    }

    /**
     * 维持双亲委派机制
     */
    public static void test2() throws Exception {
        System.out.println("MyClassLoader.loadClass前：MyObjectInterface => " + MyObjectInterface.class.getClassLoader());

        MyClassLoader classLoader = new MyClassLoader("MyClassLoader");
        classLoader.setDir("/Users/snowbuffer/Documents/dev/workspaces-me/jdk-java-basic/thread-classloader/third_classes");
        Class<?> aClass = classLoader.loadClass("com.wangwenjun.concurrent.classloader.chapter7.MyObject");
        System.out.println("MyClassLoader.loadClass: " + aClass);
        System.out.println("MyClassLoader.loadClass, MyObject.classLoader =>  " + aClass.getClassLoader());

        System.out.println("==");

        Object obj = aClass.newInstance();
        Method method = aClass.getMethod("hello", new Class<?>[]{});
        method.invoke(obj, new Object[]{}); // 反射方式可以

        System.out.println("==");

        MyObjectInterface myObjectInterface = (MyObjectInterface) aClass.newInstance(); // 能直接引用
        System.out.println("newInstance.class.classLoader => " + myObjectInterface.getClass().getClassLoader());
        System.out.println("MyClassLoader.loadClass后：MyObject.classLoader => " + MyObjectInterface.class.getClassLoader());
        System.out.println("SimpleClassLoader.loadClass, MyObject.superInterface =>  " + myObjectInterface.getClass().getInterfaces()[0]);
        System.out.println("SimpleClassLoader.loadClass, MyObject.superInterface.classLoader =>  " + myObjectInterface.getClass().getInterfaces()[0].getClassLoader());

        myObjectInterface.hello();
    }

    /**
     * 打破双亲委派机制
     */
    public static void test1() throws Exception {
        System.out.println("SimpleClassLoader.loadClass前：MyObjectInterface => " + MyObjectInterface.class.getClassLoader());

        SimpleClassLoader classLoader = new SimpleClassLoader("MyClassLoader");
        /**
         * MyObject
         */
        classLoader.setDir("/Users/snowbuffer/Documents/dev/workspaces-me/jdk-java-basic/thread-classloader/third_classes");
        Class<?> aClass = classLoader.loadClass("com.wangwenjun.concurrent.classloader.chapter7.MyObject");
        System.out.println("SimpleClassLoader.loadClass: " + aClass);
        System.out.println("SimpleClassLoader.loadClass, MyObject.classLoader =>  " + aClass.getClassLoader());
        System.out.println("SimpleClassLoader.loadClass, MyObject.superInterface =>  " + aClass.getInterfaces()[0]);
        System.out.println("SimpleClassLoader.loadClass, MyObject.superInterface.classLoader =>  " + aClass.getInterfaces()[0].getClassLoader());


        System.out.println("==");

        Object obj = aClass.newInstance();
        Method method = aClass.getMethod("hello", new Class<?>[]{});
        method.invoke(obj, new Object[]{}); // 反射方式可以

        System.out.println("==");
        System.out.println("SimpleClassLoader.loadClass后：MyObjectInterface.classLoader => " + MyObjectInterface.class.getClassLoader());

        // MyObjectInterface 与 SimpleClassLoader.loadClass加载的对象没有父子关系，因此无法进行类型转换
        MyObjectInterface myObjectInterface = (MyObjectInterface) aClass.newInstance(); // 不能直接引用 运行时包不同
        System.out.println(myObjectInterface.getClass().getClassLoader());
        myObjectInterface.hello();
    }
}
