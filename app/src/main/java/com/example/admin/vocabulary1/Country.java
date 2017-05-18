package com.example.admin.vocabulary1;

/**
 * Created by User on 13.04.2017.
 */

public class Country {
    String country;
    String capital;
    long id;

    public Country(String word, String translate, long id)
    {
        this.country = word;
        this.capital = translate;
        this.id = id;
    }

    public String GetCountry(){return country;}
    public String GetCapital(){return capital;}
    public long getId() { return id;}
}
