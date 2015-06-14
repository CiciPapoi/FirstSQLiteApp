package com.example.user.firstsqliteapp.database;

/**
 * Created by user on 10.06.2015.
 */
public interface CategoryColumns extends BaseColumns {
    String CAT = "name";
    String NR = "nritems";

    String[] ALL_COLUMNS = new String[] {ID,CAT,NR};
}
