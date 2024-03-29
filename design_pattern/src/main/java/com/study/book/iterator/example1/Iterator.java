package com.study.book.iterator.example1;

/**
 * 迭代器接口，定义访问和遍历元素的操作
 */
public interface Iterator {
    /**
     * 移动到聚合对象的第一个位置
     */
    public void first();

    /**
     * 移动到聚合对象的下一个位置
     */
    public void next();

    /**
     * 判断是否已经移动聚合对象的最后一个位置
     *
     * @return true表示已经移动到聚合对象的最后一个位置，
     * false表示还没有移动到聚合对象的最后一个位置
     */
    public boolean isDone();

    /**
     * 获取迭代的当前元素
     *
     * @return 迭代的当前元素
     */
    public Object currentItem();
}

