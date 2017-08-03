package com.example.juan.weatherapp;

import java.util.Date;

/**
 * Created by Juan on 6/27/2017.
 */

public class Favorite {
    String city, state, temperature;
    Date storingDate;

    public Favorite(String city, String state, String temperature, Date storingDate) {
        this.city = city;
        this.state = state;
        this.temperature = temperature;
        this.storingDate = storingDate;
    }
}
