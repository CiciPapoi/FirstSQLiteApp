package com.example.user.firstsqliteapp.database;

import android.database.Cursor;

/**
 * Created by user on 16.04.2015.
 */
public abstract class RowListener {
    protected abstract boolean onRow(Cursor cursor);
}
