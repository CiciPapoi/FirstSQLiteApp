package com.example.user.firstsqliteapp.business;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import com.example.user.firstsqliteapp.database.DatabaseOpenHelper;

/**
 * Created by user on 15.04.2015.
 */
public class SelectLoader extends AsyncTaskLoader<Cursor> {
    private DatabaseOpenHelper myDbHelper;
    public SelectLoader(Context context) {
        super(context);
        myDbHelper = DatabaseOpenHelper.getInstance(context);
    }
    @Override public Cursor loadInBackground(){
        return myDbHelper.getAllUsers();
    }
}
