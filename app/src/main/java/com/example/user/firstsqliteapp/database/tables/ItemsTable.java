package com.example.user.firstsqliteapp.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.DBTable;
import com.example.user.firstsqliteapp.database.ItemsColumns;
import com.example.user.firstsqliteapp.utils.TextUtils;

import java.util.ArrayList;

/**
 * This class handles basic operations on the Items table
 */
public class ItemsTable extends DBTable<Item> implements ItemsColumns {
    // STATIC FIELDS

    private static final String TABLE_NAME = "Item";
    private static final String TAG = ItemsTable.class.getSimpleName();

    // CONSTRUCTORS
    public ItemsTable(){ super(TABLE_NAME, ALL_COLUMNS); }




    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------
    @Override
    protected Item newItem() {

        return new Item();
    }

    @Override
    protected boolean onPreInsert(Item user) {
        return true;
    }

    @Override
    protected Item onPostInsert(Item item, long _id) {
        item.set_id(_id);
        return item;
    }

    @Override
    protected Item onPostFindId(Item item, long _id) {
        item.set_id(_id);
        return item;
    }

    @Override
    protected boolean onPreDelete(Item item) {
        return true;
    }

    @Override
    protected boolean onPreUpdate(Item item) {
        return true;
    }

    @Override
    protected Item onPostUpdate(Item item) {
        return null;
    }

    @Override
    protected long getIdOf(Item item) {
        return item.get_id();
    }


    @Override
    public Item populate(Item item, Cursor cursor) {
        int iterator = 0;
        item.set_id(cursor.getInt(iterator++));
        item.setCategory_id(cursor.getInt(iterator++));
        item.setStyle_id(cursor.getInt(iterator++));
        item.setRegister_date(cursor.getString(iterator++));
        item.setLast_used_date(cursor.getString(iterator++));
        item.setPhoto_path(cursor.getString(iterator));

        return item;
    }

    @Override
    public ContentValues populate(ContentValues values, Item item) {

        values.put(CAT,item.getCategory_id());
        values.put(STYLE, item.getStyle_id());
        values.put(REG, item.getRegister_date());
        values.put(LAST, item.getLast_used_date());
        values.put(URL, item.getPhoto_path());
        values.put(COL, item.getColors());
        values.put(MATCH, item.getMatches());
        Log.d(TAG, "populate with Id: " + item.get_id() );
        return values;
    }

    @Override
    protected Item findItem(Item item) {
        mDatabase.beginTransaction();
        Item result = new Item();

        try {
            String whereClause = "";

            if (item.get_id()!= -1) {
                //We have both columns available
                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_DOUBLE_COLUMNS,
                        ID, DatabaseUtils.sqlEscapeString(String.valueOf(item.get_id())));

            }
// else if (user.getUsername()!="" && user.getInitials() == "") {
//                //We have just a ro word available
//                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
//                        USR, DatabaseUtils.sqlEscapeString(user.getUsername()));

//            } else {
//                //We have only es word
//
//                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
//                        USR, DatabaseUtils.sqlEscapeString(user.getUsername()));
//            }

            Cursor cursor = mDatabase.query(mTableName, null, whereClause, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = new Item(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            }
            if (cursor != null) {
                cursor.close();
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }

        return result;
//        return null;

    }




    @Override
    protected void updateItem(Item item1, Item item2) {
        mDatabase.beginTransaction();

        try {
            String whereClause;
            whereClause="";
            if (item1.get_id() != -1) {
                //We have the id column
                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
                        ID, DatabaseUtils.sqlEscapeString(String.valueOf(item1.get_id())));
            }

            String strFilter = "_id=" + 4;
            ContentValues args = new ContentValues();

            args.put(CAT, item2.getCategory_id());
            args.put(STYLE, item2.getStyle_id());
            args.put(REG, item2.getRegister_date());
            args.put(LAST, item2.getLast_used_date());
            args.put(URL, item2.getPhoto_path());
            args.put(COL,item2.getColors());
            args.put(MATCH, item2.getMatches());

            mDatabase.update(mTableName, args, whereClause , null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }



    @Override
    protected ArrayList<Item> findItems(Item item) {
        ArrayList<Item> results = new ArrayList<Item>();

        return results;
    }

    @Override
    protected ArrayList<Item> getAllItems() {
        ArrayList<Item> results = new ArrayList<Item>();
        //Log.d(TAG,"Entered in GetAll");

        mDatabase.beginTransaction();
        try {
            Cursor cursor = mDatabase.query(mTableName, null, null, null, null, null, null, null);
            //Log.d(TAG, "After query");
            cursor.moveToFirst();
            if (cursor != null) {
                while (!cursor.isAfterLast()) {
                    results.add(new Item(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
              //      Log.d(TAG, "id:" + cursor.getString(0) + "  cat_id:" + cursor.getString(1) + "  style_id:" + cursor.getString(2) + "  registered:" + cursor.getString(3) + "  last_used:" + cursor.getString(4) + "  URI:" + cursor.getString(5) );
                    cursor.moveToNext();
                }
            } else {
                //Log.d(TAG, "cursor is null");
            }
            mDatabase.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            mDatabase.endTransaction();
        }
        return results;
    }

    @Override
    protected String[] getCreateTableSql(Context context) throws Exception {
        return new String[0];
    }

    @Override
    protected String[] getUpgradeTableSql(Context context, int oldVersion, int newVersion) throws Exception {
        return new String[0];
    }


}
