package com.study.book.prototype.example8;

public interface Prototype {
    public Prototype clone();

    public String getName();

    public void setName(String name);
}