package com.example.user.firstsqliteapp.data;

/**
 * Created by user on 15.04.2015.
 */
public class User {
    private String username;
    private String initials;
    private String address;
    private long id;



    public User() {
     }
    public User(long id, String username, String initials, String address) {
        this.id = id;
        this.username = username;
        this.initials = initials;
        this.address = address;
    }
    public User( String username, String initials, String address) {

        this.username = username;
        this.initials = initials;
        this.address = address;
    }
    //GETTERS
    public String getUsername() {
        return username;
    }

    public String getInitials() {
        return initials;
    }

    public String getAddress() {
        return address;
    }

    public long getId() {
        return id;
    }

    //SETTERS
    public void setUsername(String username) {
        this.username = username;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setId(long id) {
        this.id = id;
    }
}
