package com.example.user.firstsqliteapp.data;

/**
 * Created by user on 10.06.2015.
 */
public class Category {
    private long id;
    private String name;
    private int nritems;

    public Category(int id, String name, int nritems) {
        this.id = id;
        this.name = name;
        this.nritems = nritems;
    }

    public Category(int newId) {
        this.id = newId;
    }
    public Category() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNritems() {
        return nritems;
    }

    public void setNritems(int nritems) {
        this.nritems = nritems;
    }
}
