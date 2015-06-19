package com.example.user.firstsqliteapp.data;

/**
 * Created by user on 16.06.2015.
 */
public class Colour {

    private long id;
    private String colours;
    private int nritems;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColours() {
        return colours;
    }

    public void setColours(String colours) {
        this.colours = colours;
    }

    public int getNritems() {
        return nritems;
    }

    public void setNritems(int nritems) {
        this.nritems = nritems;
    }
}
