package com.study.book.strategy.example6;

/**
 * 日志记录策略的接口
 */
public interface LogStrategy {
    /**
     * 记录日志
     *
     * @param msg 需记录的日志信息
     */
    public void log(String msg);
}
