package com.example.user.firstsqliteapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.user.firstsqliteapp.utils.TextUtils;

import java.util.ArrayList;

/**
 * User: Cici
 */
public abstract class DBTable<T> {

    // FIELDS
    /**
     * The type of items this instance of the table is dealing with.
     */
    protected Class<T> mItemType;
    /**
     * The table name this instance is using.
     */
    protected final String mTableName;
    /**
     * The column names for the underlying table.
     */
    protected final String[] mColumnNames;
    /**
     * Reference to the SQLiteDatabase
     */
    protected SQLiteDatabase mDatabase;

    /**
     * Reference to the creating context (typically the application context)
     */
    protected Context mContext;

    //var for Logcat
    private static final String TAG = DBTable.class.getSimpleName();

    // CONSTRUCTORS

    /**
     * An abstract class by definition cannot be created directly. It can only be created by creating an instance of a
     * derived type. Therefore the only types that should have access to a constructor are derived types and hence
     * protected makes much more sense that public.
     *
     * @param mTableName   The name of the table that is going to be instantiated by one of the subclasses that extends
     *                     this one
     * @param mColumnNames The column names for the table described by mTableName
     */
    protected DBTable(String mTableName, String[] mColumnNames) {
        this.mTableName = mTableName;
        this.mColumnNames = mColumnNames;
    }

    // METHODS
    /**
     * Query the given table, returning a {@link Cursor} over the result set.
     *
     * @param table         The table name to compile the query against.
     * @param columns       A list of which columns to return. Passing null will
     *                      return all columns, which is discouraged to prevent reading
     *                      data from storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return, formatted as an
     *                      SQL WHERE clause (excluding the WHERE itself). Passing null
     *                      will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *                      replaced by the values from selectionArgs, in order that they
     *                      appear in the selection. The values will be bound as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted as an SQL
     *                      GROUP BY clause (excluding the GROUP BY itself). Passing null
     *                      will cause the rows to not be grouped.
     * @param having        A filter declare which row groups to include in the cursor,
     *                      if row grouping is being used, formatted as an SQL HAVING
     *                      clause (excluding the HAVING itself). Passing null will cause
     *                      all row groups to be included, and is required when row
     *                      grouping is not being used.
     * @param orderBy       How to order the rows, formatted as an SQL ORDER BY clause
     *                      (excluding the ORDER BY itself). Passing null will use the
     *                      default sort order, which may be unordered.
     * @param limit         Limits the number of rows returned by the query,
     *                      formatted as LIMIT clause. Passing null denotes no LIMIT clause.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
     */
    private Cursor dbQuery(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
                           String having, String orderBy, String limit) {
        Cursor cursor = null;

        //Create a retry mechanism that will make a number of minimum 5 attempts before returning
        for (int iterator = 0; iterator < 5; iterator++) {
            try {
                cursor = mDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            } catch (SQLiteException exception) {
                //Wait a little before attempting another time.
                try {
                    Thread.sleep(2);
                } catch (InterruptedException iex) {
                    break;
                }
            }
        }
        return cursor;
    }

    /**
     * Create a new item
     */
    protected abstract T newItem();

    /**
     * Return a list of strings that will be used to create the underlying table when needed.
     */
    protected abstract String[] getCreateTableSql(Context context) throws Exception;

    /**
     * Return a list of strings that will be used to upgrade the underlying table when the version number is incremented.
     */
    protected abstract String[] getUpgradeTableSql(Context context, int oldVersion, int newVersion) throws Exception;

    /**
     * Called before insert of an item. Return true to allow the insert, false to prevent it.
     */
    protected abstract boolean onPreInsert(T t);

    /**
     * Called to take any action on an item that has just been inserted into the underlying table.
     */
    protected abstract T onPostInsert(T t, long _id);

    /**
     * Called to take any action on the item used to search for it's id
     */
    protected abstract T onPostFindId(T t, long _id);
    /**
     * Called before the specified item is deleted. Return true to allow the delete, false to prevent it.
     */
    protected abstract boolean onPreDelete(T t);

    /**
     * Called just after the given item is deleted from the underlying table.
     */
    protected T onPostDelete(T t) {
        return t;
    }

    /**
     * Called just before the given item is updated. Return false to prevent the update, or true to allow it.
     */
    protected abstract boolean onPreUpdate(T t);

    /**
     * Called just after the given item is updated in the underlying table.
     */
    protected abstract T onPostUpdate(T t);

    /**
     * Return the id of the specified item.
     */
    protected abstract long getIdOf(T t);

    /**
     * Return an optional order-by field name for the underlying table when a select is run on it.
     */
    protected String getOrderByForFindAll() {
        return null;
    }

    /**
     * Close this table. The exact meaning of "close" is left to implementations to decide. It does <em>not</em> typically
     * involve closing the database.
     */
    protected void close() {
    }


    /**
     * The implementation will be made on each table and it will update object t1 with the new values from object t2.
     */
    protected abstract void updateItem(T t1, T t2);

    /**
     * The implementation will be made on each table and it will return an item with complete fields.
     */
    protected abstract T findItem(T t);

    /**
     * The implementation will be made on each table and it will return a list of items with complete fields.
     */
    protected abstract ArrayList<T> findItems(T t);

    /**
     * The implementation will be made on each table and it will return a list of all items from the table.
     */
    protected abstract ArrayList<T> getAllItems();

    /**
     * Populate the given item from the given cursor, and return the item to allow method call chaining.
     */
    public abstract T populate(T t, Cursor cursor);

    /**
     * Populate the given ContentValues item with attributes from the specified item, and return it.
     */
    public abstract ContentValues populate(ContentValues values, T t);

    public String getTableName() {
        return mTableName;
    }

    public String[] getColumnNames() {
        return mColumnNames;

    }

    /**
     * Called when the database is being created. Implementation can take whatever appropriate actions here.
     */
    public void onDatabaseCreate(Context context) throws Exception {
    }

    /**
     * Called before the database is upgraded.
     */
    public void onBeforeDatabaseUpgrade(Context context, int oldVer, int newVer) throws Exception {
    }

    /**
     * Called after the database is upgraded.
     */
    public void onAfterDatabaseUpgrade(Context context, int oldVer, int newVer) throws Exception {
    }

    /**
     * Set the SQLiteDatabase to the given value.
     */
    public DBTable<T> setDb(SQLiteDatabase db) {
        mDatabase = db;
        return this;
    }

    /**
     * Set the context to the given context. Return <code>this</code> to allow method call chaining.
     */
    public DBTable<T> setContext(Context context) {
        mContext = context;
        return this;
    }

    /**
     * Set the type of the item being dealt with by this table.
     */
    @SuppressWarnings("unchecked")
    public DBTable<T> setItemType(Class<?> itemType) {
        mItemType = (Class<T>) itemType;
        return this;
    }

    /**
     * Return an instance of the child type for the given id, or null if no such item is found.
     */
    public T findById(int id) throws SQLiteException {
        T type = null;

        Cursor cursor = dbQuery(mTableName, mColumnNames, String.format(TextUtils.FORMAT_STRING_EQUAL_DEC, BaseColumns.ID, id),
                null, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                type = populate(newItem(), cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return type;
    }

    /**
     * Walk over the cursor from start to finish, calling the specified
     * RowListener for each record until the listener returns false, or the end
     * of the data set is reached.
     */
    public void forEachRowIn(Cursor cursor, RowListener listener) {

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        if (!listener.onRow(cursor)) {
                            break;
                        }
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }

    }

    /**
     * Walk over the cursor from start to finish, calling the specified
     * ItemListener for each record.
     */
    public void forEachItemIn(Cursor cursor, final ItemListener<T> listener) {
        forEachRowIn(cursor, new RowListener() {
            @Override
            public boolean onRow(Cursor c) {
                return listener.onItem(populate(newItem(), c));
            }
        });
    }

    /**
     * Return a Cursor for the specified query.
     */
    public Cursor queryFor(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDatabase.query(mTableName, mColumnNames, selection, selectionArgs, groupBy, having, orderBy);
    }

    /**
     * Return a list of all items found in this table.
     */
    public ArrayList<T> findAll() throws SQLiteException {
        final ArrayList<T> resultsList = new ArrayList<T>();

        forEachItemIn(queryFor(null, null, null, null, getOrderByForFindAll()), new ItemListener<T>() {
            @Override
            protected boolean onItem(T item) {
                boolean itemOk = (item != null);

                if (itemOk) {
                    resultsList.add(item);
                }
                return itemOk;
            }
        });

        return resultsList;
    }

    /**
     * Insert the given item into the table, and return it.
     */
    public T insert(T item) {
        mDatabase.beginTransaction();
        try {
            if (onPreInsert(item)) {
                ContentValues values = populate(new ContentValues(), item);
                if (values != null) {
                    long id = mDatabase.insert(mTableName, null, values);
                    Log.d(TAG,"Inserted");
                    item = onPostInsert(item, id);
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return item;
    }


    /**
     * Method used to delete all the items in database.
     */
    public void clear() {
        try {
            mDatabase.beginTransaction();
            mDatabase.delete(mTableName, null, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * Delete the given item from the table. Return a reference for any chain commands.
     */
    public T delete(T item) {
        mDatabase.beginTransaction();
        try {
            if (onPreDelete(item)) {
                ContentValues values = populate(new ContentValues(), item);
                if (values != null) {
                    String whereClause;
                    Log.d(TAG, "GetIdOfItem: "+getIdOf(item));  // OK
                    if (getIdOf(item) == -1) {
                        T completeItem = findItem(item);
                        whereClause = BaseColumns.ID + " = " + getIdOf(completeItem);
                    } else {
                        whereClause = BaseColumns.ID + " = " + getIdOf(item);
                    }
                    int deletedItems = mDatabase.delete(mTableName, whereClause, null);
                    Log.d(TAG, "DELETED: "+item.toString());
                    onPostDelete(item);
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }

        return item;
    }

    /**
     * Find id for the given item in the table. Return a reference for any chain calls.
     *
     * @param item
     * @hide
     */
    public T findID(T item) {
        mDatabase.beginTransaction();
        try {

            ContentValues values = populate(new ContentValues(), item);
            if (values.get(BaseColumns.ID) == null) {
                //Search for the id based on other values

                StringBuilder whereClause = new StringBuilder();
                for (String key : values.keySet()) {
                    whereClause.append(key).append("=").append(values.get(key)).append(" and ");
                }
                whereClause.delete(whereClause.length() - 5, whereClause.length());

                Cursor cursor = mDatabase.query(mTableName, null, whereClause.toString(), null, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    long id = cursor.getInt(0);
                    onPostFindId(item, id);
                }
            }

            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }

        return item;
    }



}
