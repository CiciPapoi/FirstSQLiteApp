package com.example.user.firstsqliteapp.database;


/**
 * Created by user on 21.05.2015.
 */
public interface ItemsColumns extends BaseColumns{

    String CAT = "category_id";
    String STYLE = "style_id";
    String REG = "register_date";
    String LAST = "last_used_date";
    String URL = "photo_url";

    String[] ALL_COLUMNS = new String[] {ID,CAT,STYLE, REG, LAST, URL};
}
