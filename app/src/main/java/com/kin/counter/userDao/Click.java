package com.kin.counter.userDao;

public class Click {
    public int id;
    public int counterId;
    public String date;
    public double longitude;
    public double latitude;
    public int negate;

    public Click (Click click) {
        this.id = click.id;
        this.counterId = click.counterId;
        this.date = click.date;
        this.longitude = click.longitude;
        this.latitude = click.latitude;
        this.negate = click.negate;
    }
}
