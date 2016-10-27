package com.kin.counter.userDao;

import java.io.Serializable;

public class Counter implements Serializable {
    public int id;
    public String name;
    public int count;
    public int step;

    public Counter (String name, int count) {
        this(-1, name, count, 1);
    }

    public Counter (String name, int count, int step) {
        this(-1, name, count, step);
    }

    public Counter (int id, String name, int count, int step) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.step = step;
    }

    public Counter (Counter counter) {
        this.id = counter.id;
        this.name = counter.name;
        this.count = counter.count;
        this.step = counter.step;
    }
}
