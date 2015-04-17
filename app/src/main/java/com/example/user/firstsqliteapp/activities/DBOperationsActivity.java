package com.example.user.firstsqliteapp.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.firstsqliteapp.database.DatabaseOpenHelper;
import com.example.user.firstsqliteapp.R;

import java.io.IOException;

import com.example.user.firstsqliteapp.fragments.InsertFragment;
import com.example.user.firstsqliteapp.fragments.SelectFragment;

/**
 * Created by user on 23.03.2015.
 */
public class DBOperationsActivity extends Activity{
    private Button selectButton, insertButton, addButton;
    private DatabaseOpenHelper myDbHelper;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_operations);

        // -----------------------  DB STUFF ------------------------

        // FIRST : check if the database is already created

        //For singleton class
        myDbHelper = DatabaseOpenHelper.getInstance(this);

       // myDbHelper = new DatabaseOpenHelper(this);

        try {
            Log.d("!!!!!!!", "I am trying to create the Database");
            // check if database exists in app path, if not copy it from assets
            myDbHelper.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        // Open it:
        try {
            // open the database
            myDbHelper.open();
            myDbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            new Error("Unable to open database");
        }

 /*

        // insert user in the database

        Log.d("Try to add a new user","");
        long id = myDbHelper.insertUser("test user", "tu", "some address");

        modify the first user
        myDbHelper.updateUser(1, "user changed", "uc", "address changed");

        retrieve a particular use
        Log.d("Try to get user","");
        Cursor c = myDbHelper.getUser(2);


        Toast.makeText(this,
                "id: " + c.getInt(0) + "\n name: " + c.getString(1)
                        + "\n initials: " + c.getString(2) + "\n address: "
                        + c.getString(3), Toast.LENGTH_LONG)
                .show();

        //delete one
        myDbHelper.deleteContact(10);
        myDbHelper.deleteContact(11);
    */


        //------------------------------------------------------------------------------

    }


    public void selectBetweenFragments(View view){
        Fragment fragment;
        if ( view == findViewById(R.id.select_button_id)){
            fragment = new SelectFragment();
        }
        else {
            fragment = new InsertFragment();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place_id, fragment);
        fragmentTransaction.commit();

    }



}
