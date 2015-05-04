package com.example.user.firstsqliteapp;

import android.app.Application;
import com.example.user.firstsqliteapp.database.DatabaseManager;

/**
 * The application class for our project.
 * User: wraith
 */
public class MyApp extends Application {

    private static MyApp sInstance = null;

    public static MyApp getInstance() {
        return sInstance;
    }

    public MyApp() {
        super();
        sInstance = this;
    }

    /**
     * Method that will ensure that all the needed information and parameters are set, before accessing database
     */
    private void ensureDatabase() {
        DatabaseManager.getInstance().initDatabase(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ensureDatabase();
    }
}
