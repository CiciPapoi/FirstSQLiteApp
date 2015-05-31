package com.example.user.firstsqliteapp.data;

/**
 * Created by user on 10.05.2015.
 */
public class Item {
    private long _id;
    private long category_id;
    private long style_id;
    private String register_date;
    private String last_used_date;
    private String photo_path;


    /*
        Constructors
     */
    public Item() {
    }

    public Item(long _id, long category_id, long style_id, String register_date, String last_used_date, String photo_item) {
        this._id = _id;
        this.category_id = category_id;
        this.style_id = style_id;
        this.register_date = register_date;
        this.last_used_date = last_used_date;
        this.photo_path = photo_item;
    }

    public Item(long category_id, long style_id, String register_date, String last_used_date, String photo_item) {
        this.category_id = category_id;
        this.style_id = style_id;
        this.register_date = register_date;
        this.last_used_date = last_used_date;
        this.photo_path = photo_item;
    }

    /*
        Getters and Setters
     */
    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getStyle_id() {
        return style_id;
    }

    public void setStyle_id(long style_id) {
        this.style_id = style_id;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getLast_used_date() {
        return last_used_date;
    }

    public void setLast_used_date(String last_used_date) {
        this.last_used_date = last_used_date;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }


}
