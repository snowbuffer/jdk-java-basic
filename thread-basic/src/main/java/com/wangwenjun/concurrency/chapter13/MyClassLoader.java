package com.wangwenjun.concurrency.chapter13;

import sun.java2d.pipe.SpanShapeRenderer;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String name;//类加载器的名字

    private String path = "E:/"; // 加载的类的路劲

    public MyClassLoader(String name) {
        super(); // 让系统类加载器成为该加载器的父加载器
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public MyClassLoader(ClassLoader parent, String name) {
        super(parent); // 显示指定父加载器
        this.name = name;
    }

    @Override
    protected Class<?> findClass(String name)
            throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        File classFile = new File(path, classPath + ".class");
        if (!classFile.exists()) {
            throw new ClassNotFoundException("The class " + name + " not found under " + path);
        }

        byte[] classBytes = loadClassBytes(classFile);
        if (null == classBytes || classBytes.length == 0)
            throw new ClassNotFoundException("load the class " + name + " failed");

        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    //java建议这样去加载class文件二进制字节码
    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


//    public static void main(String[] args) throws Exception {
//
//        //不显示执行父加载器，那么loader1的父加载器是系统类加载器
//        MyClassLoader loader1 = new MyClassLoader("loader1");
//        loader1.setPath("E:\\myapp\\serverlib\\");//此目录有Smaple、Dog class文件
//
//        //loader2的父加载器是loader1
//        MyClassLoader loader2 = new MyClassLoader(loader1, "loader2");
//        loader2.setPath("E:\\myapp\\clientlib\\");//此目录无Smaple、Dog class文件
//
//        //父加载设置为null, 那么loader3父载器就是根类加载器  java -cp .;E:\myapp\serverlib\ com.wangwenjun.concurrency.chapter13.MyClassLoader
//        MyClassLoader loader3 = new MyClassLoader(null, "loader3");
//        loader3.setPath("E:\\myapp\\otherlib\\");//此目录有Smaple、Dog class文件
//
//        test(loader2);
//        test(loader3);
//
//        /**
//         * 1.执行java -cp .;E:\myapp\serverlib\ com.wangwenjun.concurrency.chapter13.MyClassLoader，-cp参数：增加classpath路径，输出结果：
//         * Smaple is loaded by sun.misc.Launcher$AppClassLoader@18b4aac2 //系统加载器
//         * Dog is loaded by sun.misc.Launcher$AppClassLoader@18b4aac2 //系统加载器
//         * Smaple is loaded by loader3
//         * Dog is loaded by loader3
//         * 解析：
//         * (1)loader2向上父委托，根类加载器无法加载Smaple、Dog，扩展加载器无法加载Smaple、Dog，系统加载器是加载classpath中的类，由于java命令中将类的路径增加到了classpath中，所以由系统加载器加载Smaple、Dog
//         * (2)系统加载器是Smaple、Dog类的定义类加载器；系统类加载器、loader1、loader2加载器都是Smaple、Dog类的初始类加载器
//         * (3)loader3向上父委托，根类加载器无法加载Smaple、Dog，所以由loader3自己加载，loader3加载器是Smaple、Dog类的定义类加载器和初始类加载器
//         *
//         * 2.执行java com.wangwenjun.concurrency.chapter13.MyClassLoader，输出结果：
//         * Smaple is loaded by loader1
//         * Dog is loaded by loader1
//         * Smaple is loaded by loader3
//         * Dog is loaded by loader3
//         * 解析：
//         * (1)loader2向上父委托，根类加载器无法加载Smaple、Dog，扩展加载器无法加载Smaple、Dog，由于没有增加类路径到classpath中，所以系统加载器无法加载Smaple、Dog，而loader1加载目录中有Smaple、Dog class文件，所以loader1可以加载
//         * (2)虽然loader2加载器加载目录中没有Smaple、Dog class文件，但是已经由loader1加载了
//         */
//    }


//    public static void main(String[] args) throws Exception {
//
//        //loader1的父加载器是系统类加载器
//        MyClassLoader loader1 = new MyClassLoader("loader1");
//        loader1.setPath("e:\\myapp\\serverlib\\");
//
//        Class<?> clazz = loader1.loadClass("com.wangwenjun.concurrency.chapter13.Sample");
//        Object object = clazz.newInstance();
//        Sample Sample = null; // 这句没有报错，因为开始加载类
//        Sample = (Sample) object; // 先loadClass,再进行强制类型转换
//        System.out.println(Sample.a);
//
//        /**
//         * 执行java com.wangwenjun.concurrency.chapter13.MyClassLoader，输出结果：
//         * Exception in thread "main" java.lang.NoClassDefFoundError: com/wangwenjun/concurrency/chapter13/Sample
//         *         at com.wangwenjun.concurrency.chapter13.MyClassLoader.main(MyClassLoader.java:113)
//         * Caused by: java.lang.ClassNotFoundException: com.wangwenjun.concurrency.chapter13.Sample
//         *         at java.net.URLClassLoader.findClass(Unknown Source)
//         *         at java.lang.ClassLoader.loadClass(Unknown Source)
//         *         at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
//         *         at java.lang.ClassLoader.loadClass(Unknown Source)
//         *         ... 1 more
//         * 也就是这一句出错了：Sample Sample = (Sample) object;
//         * 解析：
//         * MyClassLoader类是由系统类加载的，而Sample类是由loader1类加载的，父加载器加载的类不能看见子加载器加载的类，
//         * 因此MyClassLoader类看不见Sample类，所以在MyClassLoader类中的main方法中使用Sample类会导致上面错误。
//         */
//    }


    /*public static void main(String[] args) throws Exception {

        //不显示执行父加载器，那么loader1的父加载器是系统类加载器
        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("E:\\myapp\\serverlib\\");//此目录有Smaple class文件，没有Dog文件

        //loader2的父加载器是loader1
        MyClassLoader loader2 = new MyClassLoader(loader1, "loader2");
        loader2.setPath("E:\\myapp\\clientlib\\");//此目录只有Dog class文件

        test(loader2);

        *//**
         * 执行java com.wangwenjun.concurrency.chapter13.MyClassLoader，输出结果：
         * Smaple is loaded by loader1
         * Exception in thread "main" java.lang.NoClassDefFoundError: com/wangwenjun/concurrency/chapter13/Dog
         *         at com.wangwenjun.concurrency.chapter13.Sample.<init>(Sample.java:9)
         *         at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
         *         at sun.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)
         *         at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)
         *         at java.lang.reflect.Constructor.newInstance(Unknown Source)
         *         at java.lang.Class.newInstance(Unknown Source)
         *         at com.wangwenjun.concurrency.chapter13.MyClassLoader.test(MyClassLoader.java:167)
         *         at com.wangwenjun.concurrency.chapter13.MyClassLoader.main(MyClassLoader.java:144)
         * Caused by: java.lang.ClassNotFoundException: The class com.wangwenjun.concurrency.chapter13.Dog not found under E:\myapp\serverlib\
         *         at com.wangwenjun.concurrency.chapter13.MyClassLoader.findClass(MyClassLoader.java:36)
         *         at java.lang.ClassLoader.loadClass(Unknown Source)
         *         at java.lang.ClassLoader.loadClass(Unknown Source)
         *         ... 8 more
         *
         * 为什么输出结果不是
         * Smaple is loaded by loader1
         * Dogis loaded by loader2
         * 解析：
         * loader2父委托loader1了，loader1加载目录里面有Sample类，没有Dog类，所以loader1只加载了Sample类，loader2加载目录有Dog类，
         * Dog类应该由loader2自己加载，为什么会报错了呢？
         * 其实Dog类已经被loader2加载，只是没有输出Dog is loaded by loader2这一句而已。
         * 因为父加载器加载的类看不到子加载器加载的类的问题，所以在Sample的构造方法里面使用Dog类时就已经报错了，代码不会在往下执行了。
         *//*
    }*/
    public static void main(String[] args) throws Exception {

        MyClassLoader loader2 = new MyClassLoader(null, "loader2");
        loader2.setPath("E:\\myapp\\otherlib\\");//此目录有Smaple、Dog class文件
//        loader2.loadClass("com.wangwenjun.concurrency.chapter13.Sample"); // 先加载一次

        //父加载设置为null, 那么loader3父载器就是根类加载器  java -cp .;E:\myapp\serverlib\ com.wangwenjun.concurrency.chapter13.MyClassLoader
        MyClassLoader loader3 = new MyClassLoader(loader2, "loader3");
        loader3.setPath("E:\\myapp\\otherlib\\");//此目录有Smaple、Dog class文件
        Class<?> clazz = loader3.loadClass("com.wangwenjun.concurrency.chapter13.Sample");
        loader3.loadClass("com.wangwenjun.concurrency.chapter13.Sample");
        System.out.println(clazz);
        Object object3 = clazz.newInstance();
        System.out.println(object3);

        System.out.println();
        MyClassLoader loader4 = new MyClassLoader(null, "loader4");
        loader4.setPath("E:\\myapp\\otherlib\\");//此目录有Smaple、Dog class文件
        Class<?> clazz4 = loader4.loadClass("com.wangwenjun.concurrency.chapter13.Sample");
        System.out.println(clazz4);
        Object object4 = clazz4.newInstance();
        System.out.println(object4);

        System.out.println(clazz == clazz4);
        Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass("com.wangwenjun.concurrency.chapter13.Sample");

        Sample sample = new Sample();
        System.out.println(sample.getClass().getClassLoader());
        System.out.println(aClass == clazz);
        System.out.println(aClass == clazz4);
        try {
            sample = (Sample) object4;
        } catch (Exception e) {
            System.out.println("类型转换失败");
        }
        Serializable aa = (Serializable) clazz4.newInstance();
        System.out.println(Serializable.class.getClassLoader());
        System.out.println(aa.getClass().getClassLoader());
        System.out.println(aa.getClass());

        AAAA BBB = (AAAA) clazz4.newInstance();
    }

    // java -cp .;E:\myapp\serverlib\ com.wangwenjun.concurrency.chapter13.MyClassLoader

    public static void test(ClassLoader loader) throws Exception {

        Class<?> clazz = loader.loadClass("com.wangwenjun.concurrency.chapter13.Sample");
        Object object = clazz.newInstance();
    }
}