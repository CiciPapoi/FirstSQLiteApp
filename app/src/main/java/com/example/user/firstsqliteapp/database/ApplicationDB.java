package com.example.user.firstsqliteapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.data.User;
import com.example.user.firstsqliteapp.database.tables.ItemsTable;
import com.example.user.firstsqliteapp.database.tables.UsersTable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    //var for Logcat
    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();


    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------
    protected static final HashMap<Class<?>, DBTable> sTables = new HashMap<Class<?>, DBTable>();
    // database path
    private static String DATABASE_PATH;



    // ------------------------------------------------------------------------
    // STATIC INITIALIZERS
    // ------------------------------------------------------------------------
     static {
        // Save a mapping between dto objects and their storage table
        sTables.put(User.class, new UsersTable());
        sTables.put(Item.class, new ItemsTable());

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
        DATABASE_PATH = context.getFilesDir().getParentFile().getPath() + "/databases/";


        this.mContext = context;
        this.mListener = listener;

        try {
            create();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            mApplicationDatabase = getWritableDatabase();
        }

        //Initialize all the tables in keyset
        for (Class<?> key : sTables.keySet()) {
            DBTable<?> table = sTables.get(key);
            table.setItemType(key).setDb(mApplicationDatabase).setContext(mContext);
        }
    }

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    /**
     * Creates an empty database on the system and
     * rewrites it with my own database.
     * */
    public void create() throws IOException {
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
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);

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
