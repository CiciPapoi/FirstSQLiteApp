package com.example.user.firstsqliteapp.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.example.user.firstsqliteapp.data.Category;
import com.example.user.firstsqliteapp.data.Item;
import com.example.user.firstsqliteapp.database.CategoryColumns;
import com.example.user.firstsqliteapp.database.DBTable;
import com.example.user.firstsqliteapp.database.ItemsColumns;
import com.example.user.firstsqliteapp.utils.TextUtils;

import java.util.ArrayList;

/**
 * Created by user on 10.06.2015.
 */
public class CategoryTable extends DBTable<Category> implements CategoryColumns {
    //STATIC FIELDS
    private static final String TABLE_NAME = "Category";
    private static final String TAG = CategoryTable.class.getSimpleName();

    // CONSTRUCTORS
    public CategoryTable(){ super(TABLE_NAME, ALL_COLUMNS); }

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------
    @Override
    protected Category newItem() {

        return new Category();
    }

    @Override
    protected boolean onPreInsert(Category cat) {
        return true;
    }

    @Override
    protected Category onPostInsert(Category category, long _id) {
        category.setId(_id);
        return category;
    }

    @Override
    protected Category onPostFindId(Category category, long _id) {
        category.setId(_id);
        return category;
    }

    @Override
    protected boolean onPreDelete(Category category) {
        return true;
    }

    @Override
    protected boolean onPreUpdate(Category category) {
        return true;
    }

    @Override
    protected Category onPostUpdate(Category category) {
        return null;
    }

    @Override
    protected long getIdOf(Category category) {
        return category.getId();
    }


    @Override
    public Category populate(Category category, Cursor cursor) {
        int iterator = 0;
        category.setId(cursor.getInt(iterator++));
        category.setName(cursor.getString(iterator++));
        category.setNritems(cursor.getInt(iterator++));

        return category;
    }

    @Override
    public ContentValues populate(ContentValues values, Category item) {

        values.put(CAT,item.getName());
        values.put(NR, item.getNritems());
        Log.d(TAG, "populate with Id: " + item.getId());
        return values;
    }

    @Override
    protected Category findItem(Category cat) {
//        mDatabase.beginTransaction();
//        Item result = new Item();
//
//        try {
//            String whereClause;
//
//            if (user.getUsername()!=""&& user.getInitials() != "") {
//                //We have both columns available
//                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_DOUBLE_COLUMNS,
//                        USR, DatabaseUtils.sqlEscapeString(user.getUsername()),
//                        INIT, DatabaseUtils.sqlEscapeString(user.getInitials()));
//
//            } else if (user.getUsername()!="" && user.getInitials() == "") {
//                //We have just a ro word available
//                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
//                        USR, DatabaseUtils.sqlEscapeString(user.getUsername()));
//
//            } else {
//                //We have only es word
//
//                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
//                        USR, DatabaseUtils.sqlEscapeString(user.getUsername()));
//            }
//
//            Cursor cursor = mDatabase.query(mTableName, null, whereClause, null, null, null, null, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                result = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//            }
//            if (cursor != null) {
//                cursor.close();
//            }
//            mDatabase.setTransactionSuccessful();
//        } finally {
//            mDatabase.endTransaction();
//        }
//
//        return result;
        return null;

    }




    @Override
    protected void updateItem(Category cat1, Category cat2) {
        mDatabase.beginTransaction();

        try {
            String whereClause;

            if (cat1.getId() != -1) {
                //We have the id column
                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
                        ID, DatabaseUtils.sqlEscapeString(String.valueOf(cat1.getId())));
            }

            // String strFilter = "_id=" + 4;
            String strFilter = "_id=" + cat1.getId();
            ContentValues args = new ContentValues();
            args.put(NR, cat1.getNritems()+1);
            mDatabase.update(mTableName, args, strFilter, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }



    @Override
    protected ArrayList<Category> findItems(Category item) {
        ArrayList<Category> results = new ArrayList<Category>();

        return results;
    }

    @Override
    protected ArrayList<Category> getAllItems() {
        ArrayList<Category> results = new ArrayList<Category>();
        //Log.d(TAG,"Entered in GetAll");

        mDatabase.beginTransaction();
        try {
            Cursor cursor = mDatabase.query(mTableName, null, null, null, null, null, null, null);
            //Log.d(TAG, "After query");
            cursor.moveToFirst();
            if (cursor != null) {
                while (!cursor.isAfterLast()) {
                    results.add(new Category(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
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
