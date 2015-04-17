package com.example.user.firstsqliteapp.database;

/**
 * Created by user on 16.04.2015.
 */
public interface UsersColumns extends BaseColumns {
    String USR =  "username";
    String INIT = "initials";
    String ADDR = "address";


    String[] ALL_COLUMNS = new String[]{
            ID,
            USR,
            INIT,
            ADDR
    };

}
