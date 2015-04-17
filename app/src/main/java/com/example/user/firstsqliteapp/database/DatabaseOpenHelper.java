package com.example.user.firstsqliteapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by user on 14.03.2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    //fields for my database
    private static final String DATABASE_NAME = "users";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_INITIALS = "initials";
    private static final String COLUMN_ADDRESS = "address";
    //var for Logcat
    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();

    private static DatabaseOpenHelper mInstance = null;

    // database handle
    private SQLiteDatabase database;

    //context instance
    private static Context context;

    // database path
    private static String DATABASE_PATH;


    //private constructor to prevent direct instantiation

    private DatabaseOpenHelper(Context my_context){
        super(my_context,DATABASE_NAME,null, DATABASE_VERSION);
        DATABASE_PATH = my_context.getFilesDir().getParentFile().getPath() + "/databases/";
        Log.d("path", DATABASE_PATH);
        DatabaseOpenHelper.context = my_context;
    }



/*
    //when it was not singleton: the constructor and the assign path to the DB
    public DatabaseOpenHelper(Context con){
        super(con,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = con;
        DATABASE_PATH = context.getFilesDir().getParentFile().getPath() + "/databases/";
        Log.d("path", DATABASE_PATH);
    }

*/


    //GetInstance method !!

    public static DatabaseOpenHelper getInstance(Context my_context){
        if ( mInstance == null ){
            mInstance = new DatabaseOpenHelper(my_context.getApplicationContext());
        }
        if ( mInstance != null ) {
            Log.d(TAG, "I got the instance!");
        }
        return mInstance;
    }




    /**
     * Creates an empty database on the system and
     * rewrites it with my own database.
     * */
    public void create() throws IOException{
        //Log.d("!!", "I entered the create function");
        boolean check = checkDataBase();

        //Log.d("!!", "I checked the DB");
        SQLiteDatabase db_Read = null;

        // create the empty db default system path
        db_Read = this.getWritableDatabase();
        Log.d(TAG, "I got the Writable Database");
        db_Read.close();
        try{
            if ( !check ){
                copyDataBase();
            }
        }catch(IOException io){
            throw new Error("Error copying database!");
        }

    }

    /**
     * Check if the database already exist to avoid
     * re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
        Log.d(TAG, "I am trying to open the Database");
        SQLiteDatabase checkDB = null;
        try{

            String myPath = DATABASE_PATH + DATABASE_NAME;

            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            Log.d(TAG, "I opened the Database");
        }catch(SQLiteException e){
            // database does not exist yet

        }

        if ( checkDB != null ){
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */

    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        //path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        // open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer the empty db as the output stream
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    // Open and Close the connection with the database
    /** open the database */
    public SQLiteDatabase open() throws SQLiteException {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

        return database;
    }



    /** close the database */
    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }


    //*****METHODS for working with the USERS******
    // insert a user into the database
    public long insertUser(String name, String initials, String address) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME, name);
        initialValues.put(COLUMN_INITIALS, initials);
        initialValues.put(COLUMN_ADDRESS, address);
        return database.insert(TABLE_NAME, null, (ContentValues)initialValues);
    }

    // updates a user
    public boolean updateUser(long rowId, String name, String initials,
                              String address) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_NAME, name);
        args.put(COLUMN_INITIALS, initials);
        args.put(COLUMN_ADDRESS, address);
        return database.update(TABLE_NAME, args, COLUMN_ID + "=" + rowId, null) > 0;
    }

    // retrieves a particular user
    public Cursor getUser(long rowId) throws SQLiteException {
        Cursor mCursor = database.query(true, TABLE_NAME,
                                        new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_INITIALS, COLUMN_ADDRESS },
                                        COLUMN_ID + " = " + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // delete a particular user
    public boolean deleteContact(long rowId) {
        return database.delete(TABLE_NAME, COLUMN_ID + "=" + rowId, null) > 0;
    }

    // retrieves all users
    public Cursor getAllUsers() {
        return database.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_INITIALS, COLUMN_ADDRESS }, null, null,
                null, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
