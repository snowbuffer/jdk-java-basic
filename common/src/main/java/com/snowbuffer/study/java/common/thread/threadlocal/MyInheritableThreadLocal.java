package com.snowbuffer.study.java.common.thread.threadlocal;

import com.snowbuffer.study.java.common.thread.threadlocal.domain.Person;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-11 19:54
 */
public class MyInheritableThreadLocal {

    public static ThreadLocal<Person> threadLocal1 = new InheritableThreadLocal<>();

    public static ThreadLocal<Person> threadLocal2 = new ThreadLocal<>();

    public static ThreadLocal<Person> threadLocal3 = new PersonInheritableThreadLocal();

    public static final class PersonInheritableThreadLocal extends InheritableThreadLocal<Person> {

        // 重写ThreadLocal中的方法： 每个线程.get手，都会实例化一个属于线程自己的变量
        protected Person initialValue() {
            return new Person();
        }

        // 重写InheritableThreadLocal中的方法:
        //  表示InheritableThreadLocal 复制 parent Thread.threadLocals时，采用创建新实例方式，隔离parent Thread.threadLocals
        protected Person childValue(Person parentValue) {
            if (parentValue != null) {
                // 返回浅拷贝, 以达到使子线程无法影响主线程的目的
                return parentValue.clone();
            } else {
                return null;
            }
        }
    }

    /**
     * 参考地址：https://blog.csdn.net/v123411739/article/details/79117430
     *
     * Thread.threadLocals  -> parent Thread
     * Thread.inheritableThreadLocals -> current Thread
     *
     * 复制流程：
     *  parent Thread.threadLocals 全部浅拷贝到 current Thread.inheritableThreadLocals
     *
     *  InheritableThreadLocal 重写  getMap 方法 {
     *      t.inheritableThreadLocals
     *      后续取/存数据均从inheritableThreadLocals获取，与 parent Thread.threadLocals 无任何关系
     *  }
     *
     *  InheritableThreadLocal 重写  createMap 方法 {
     *      t.inheritableThreadLocals = new ThreadLocalMap(this, firstValue);
     *      后续取/存数据均从inheritableThreadLocals获取，与 parent Thread.threadLocals 无任何关系
     *  }
     *
     *  结果：
     *      拷贝过程为浅拷贝：threadLocals copy to inheritableThreadLocals
     *      后续操作全部基于inheritableThreadLocals操作，即修改inheritableThreadLocals中的数据，不会影响 parent thread.threadLocals
     *
     */

}
