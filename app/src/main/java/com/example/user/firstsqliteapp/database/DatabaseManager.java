package com.example.user.firstsqliteapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.user.firstsqliteapp.MyApp;
import com.example.user.firstsqliteapp.data.User;

import java.io.File;
import java.util.IllegalFormatException;

/**
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseManager implements DatabaseListener {

    //fields for my database
    private static final String DATABASE_NAME = "users";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_INITIALS = "initials";
    private static final String COLUMN_ADDRESS = "address";


    private static DatabaseManager sInstance = new DatabaseManager();

    public static final DatabaseManager getInstance() {
        return sInstance;
    }

    private ApplicationDB mApplicationDatabase;   // the dbopenhelper


    private DatabaseManager() {
    }


    /**
     * Method used to insert the content of the databased file, to local database. Note that this method is designed
     * to be called from a background thread and should not be used directly from main thread.
     *
     * @param path         The path to the file containing the content that will be inserted
     * @param databaseName The name of the file containing the content that will be inserted to local database
     */
    public void batchInsertFromDatabase(String path, String databaseName) {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path + databaseName, null, 0);
        database.beginTransaction();
        try {

            Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
            DBTable table = DatabaseManager.getInstance().getTable(User.class);
            while (cursor.moveToNext()) {
                User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                table.insert(user);
            }

            database.setTransactionSuccessful();

            //Delete databsae file
            File databaseFile = new File(path, databaseName);
            databaseFile.delete();
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    /**
     * Method used to delete all values in database
     */
    public void clearData() {
        DBTable table = DatabaseManager.getInstance().getTable(User.class);
        table.clear();
    }

    public void initDatabase(Context context) {
        mApplicationDatabase = new ApplicationDB(context, this);
    }

    /**
     * Method used to insert an item in a database.
     *
     * @param table    The table in which the item is going to be inserted
     * @param item     The item to insert
     * @param callback A callback to notify the status of the operation, and also to pass back the item inserted
     */
    public <T> void insertItem(final DBTable table, final T item, final DatabaseOperationStatus callback) {
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    table.insert(item);
                } catch (SQLiteException exception) {
                    callback.onError(exception);
                } catch (IllegalFormatException illegalFormatException) {
                    callback.onError(illegalFormatException);
                } catch (NullPointerException nullPointerException) {
                    callback.onError(nullPointerException);
                }

                //If in the end everything is ok, just notify the user that the operation is complete
                callback.onComplete(item);

            }
        });
        worker.start();
    }

    /**
     * Method used to find an item in a database.
     *
     * @param table    The table in which the item is going to be searched
     * @param item     The item to search
     * @param callback A callback to notify the status of the operation, and also to pass back the item found
     */
    public <T> void findItem(final DBTable table, final T item, final DatabaseOperationStatus callback) {
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onComplete(table.findItem(item));
                } catch (SQLiteException exception) {
                    callback.onError(exception);
                } catch (IllegalFormatException illegalFormatException) {
                    callback.onError(illegalFormatException);
                } catch (NullPointerException nullPointerException) {
                    callback.onError(nullPointerException);
                }

            }
        });
        worker.start();
    }

    /**
     * Method used to search an item in a database.
     *
     * @param table    The table in which the item is going to be searched
     * @param item     The item to search
     * @param callback A callback to notify the status of the operation, and also to pass back the items found
     */
    public <T> void findItems(final DBTable table, final T item, final DatabaseOperationStatus callback) {
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onComplete(table.findItems(item));
                } catch (SQLiteException exception) {
                    callback.onError(exception);
                } catch (IllegalFormatException illegalFormatException) {
                    callback.onError(illegalFormatException);
                } catch (NullPointerException nullPointerException) {
                    callback.onError(nullPointerException);
                }

            }
        });
        worker.start();
    }

    /**
     * Method used to delete an item from database.
     *
     * @param table    The table containing the item that will be removed.
     * @param item     The item that will be removed.
     * @param callback A callback to notify the status of the operation
     */
    public <T> void deleteItem(final DBTable table, final T item, final DatabaseOperationStatus callback) {
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onComplete(table.delete(item));
                } catch (SQLiteException exception) {
                    callback.onError(exception);
                } catch (IllegalFormatException illegalFormatException) {
                    callback.onError(illegalFormatException);
                } catch (NullPointerException nullPointerException) {
                    callback.onError(nullPointerException);
                }
            }
        });
        worker.start();
    }

    /**
     * Return an instance of the table for certain type
     */
    public <T, V extends DBTable<T>> V getTable(Class<T> type) {
        DBTable<T> table = ApplicationDB.sTables.get(type);

        if (table != null) {
            table.setItemType(type).setDb(mApplicationDatabase.getApplicationDatabase()).setContext(MyApp.getInstance().getApplicationContext());
        }

        return (V) table;
    }

    @Override
    public void onBeforeDatabaseCreate(ApplicationDB db) {
    }

    @Override
    public void onAfterDatabaseCreate(ApplicationDB db) {
    }

    @Override
    public void onBeforeDatabaseUpgrade(ApplicationDB db, int oldVersion, int newVersion) {
    }

    @Override
    public void onAfterDatabaseUpgrade(ApplicationDB db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDatabaseOpened(ApplicationDB db) {
    }

    @Override
    public void onDatabaseClosed(ApplicationDB db) {
    }
}