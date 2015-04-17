package com.example.user.firstsqliteapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.example.user.firstsqliteapp.data.User;
import com.example.user.firstsqliteapp.database.tables.UsersTable;

import java.util.HashMap;

/**
 * Main DB for the app.
 * User: Cici
 */

public class ApplicationDB extends SQLiteOpenHelper {

    //fields for my database
    private static final String DATABASE_NAME = "users";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_INITIALS = "initials";
    private static final String COLUMN_ADDRESS = "address";


    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------
    protected static final HashMap<Class<?>, DBTable> sTables = new HashMap<Class<?>, DBTable>();

    // ------------------------------------------------------------------------
    // STATIC INITIALIZERS
    // ------------------------------------------------------------------------
     static {
        // Save a mapping between dto objects and their storage table
        sTables.put(User.class, new UsersTable());

    }

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------
    public static HashMap<Class<?>, DBTable> getTables() {
        return sTables;
    }
    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------
    protected SQLiteDatabase mApplicationDatabase;
    protected Context mContext;
    protected DatabaseListener mListener;


    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------
    public ApplicationDB(Context context, DatabaseListener listener) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.mContext = context;
        this.mListener = listener;

        mApplicationDatabase = getWritableDatabase();

        //Initialize all the tables in keyset
        for (Class<?> key : sTables.keySet()) {
            DBTable<?> table = sTables.get(key);
            table.setItemType(key).setDb(mApplicationDatabase).setContext(mContext);
        }
    }

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    public SQLiteDatabase getApplicationDatabase() {
        return mApplicationDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mApplicationDatabase = db;

        //Create the database in an async task, to avoid glitches or freezes
        new AsyncTask<Void, String, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                //For each table in sTable structure, import sql and execute it
                try {

                    for (DBTable<?> table : sTables.values()) {

                        for (String sqlStatement : table.getCreateTableSql(mContext)) {
                            mApplicationDatabase.execSQL(sqlStatement);
                        }

                        //Set a reference to database for each table and activate the listeners that class
                        table.setDb(mApplicationDatabase).onDatabaseCreate(mContext);
                    }


                } catch (Exception exception) {
                    //TODO: print stacktrace with TAG
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                if (mListener != null) {
                    mListener.onBeforeDatabaseCreate(ApplicationDB.this);
                }
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (mListener != null) {
                    mListener.onAfterDatabaseCreate(ApplicationDB.this);
                    mListener.onDatabaseOpened(ApplicationDB.this);
                }
            }
        }.execute();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: implement the upgrade section too
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        mApplicationDatabase = db;

        if (mListener != null) {
            mListener.onDatabaseOpened(this);
        }
    }

    @Override
    public synchronized void close() {
        super.close();

        mApplicationDatabase.close();

        for (DBTable<?> table : sTables.values()) {
            table.close();
        }
    }
}
