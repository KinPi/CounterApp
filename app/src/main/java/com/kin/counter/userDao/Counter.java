package com.kin.counter.userDao;

import java.io.Serializable;

public class Counter implements Serializable {
    public int id;
    public String name;
    public int count;
    public int increment;

    public Counter (String name, int count) {
        this(-1, name, count, 1);
    }

    public Counter (String name, int count, int increment) {
        this(-1, name, count, increment);
    }

    public Counter (int id, String name, int count, int increment) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.increment = increment;
    }

    public Counter (Counter counter) {
        this.id = counter.id;
        this.name = counter.name;
        this.count = counter.count;
        this.increment = counter.increment;
    }
}
