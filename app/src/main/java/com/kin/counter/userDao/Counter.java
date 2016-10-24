package com.kin.counter.userDao;

public class Counter {
    public int id;
    public String name;
    public int count;

    public Counter (String name, int count) {
        this(-1, name, count);
    }

    public Counter (int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public Counter (Counter counter) {
        this.id = counter.id;
        this.name = counter.name;
        this.count = counter.count;
    }
}
