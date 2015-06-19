package com.example.user.firstsqliteapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 10.05.2015.
 */
public class Item implements Parcelable{
    private long _id;
    private long category_id;
    private int style_id;
    private String register_date;
    private String last_used_date;
    private String photo_path;
    private String colors;
    private String matches;

    @Override
    public String toString() {
        return "Item{" +
                "_id=" + _id +
                ", category_id=" + category_id +
                ", style_id=" + style_id +
                ", register_date='" + register_date + '\'' +
                ", last_used_date='" + last_used_date + '\'' +
                ", photo_path='" + photo_path + '\'' +
                ", colors='" + colors + '\'' +
                ", matches='" + matches + '\'' +
                '}';
    }



    /*
            Constructors
         */
    public Item() {
    }

    public Item(long _id, long category_id, int style_id, String register_date, String last_used_date, String photo_item, String colors, String matches) {
        this._id = _id;
        this.category_id = category_id;
        this.style_id = style_id;
        this.register_date = register_date;
        this.last_used_date = last_used_date;
        this.photo_path = photo_item;
        this.colors = colors;
        this.matches = matches;
    }

    public Item(long category_id, int style_id, String register_date, String last_used_date, String photo_item, String colors, String matches) {
        this.category_id = category_id;
        this.style_id = style_id;
        this.register_date = register_date;
        this.last_used_date = last_used_date;
        this.photo_path = photo_item;
        this.colors = colors;
        this.matches = matches;
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

    public int getStyle_id() {
        return style_id;
    }

    public void setStyle_id(int style_id) {
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

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getMatches() {
        return matches;
    }

    public void setMatches(String matches) {
        this.matches = matches;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Parcelling part
    public Item(Parcel in){
        String[] data = new String[8];

        in.readStringArray(data);
        this._id = Integer.valueOf(data[0]);   // data[0];
        this.category_id = Integer.valueOf(data[1]);
        this.style_id = Integer.valueOf(data[2]);
        this.register_date = data[3];
        this.last_used_date = data[4];
        this.photo_path = data[5];
        this.colors = data[6];
        this.matches = data[7];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {  String.valueOf(this.get_id()),
                String.valueOf(this.getCategory_id()),
                String.valueOf(this.getStyle_id()),
                this.getRegister_date(),
                this.getLast_used_date(),
                this.getPhoto_path(),
                this.getColors(),
                this.getMatches()
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };



}
