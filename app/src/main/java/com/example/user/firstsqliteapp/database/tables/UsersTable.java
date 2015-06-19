package com.example.user.firstsqliteapp.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
//import android.text.TextUtils;
import android.util.Log;

import com.example.user.firstsqliteapp.data.User;
import com.example.user.firstsqliteapp.database.AccessConstants;
import com.example.user.firstsqliteapp.database.DBTable;
import com.example.user.firstsqliteapp.database.UsersColumns;
import com.example.user.firstsqliteapp.utils.TextUtils;


import java.util.ArrayList;
import java.util.Dictionary;

/**
 * This class handles basic operations on the users table
 */
public class UsersTable extends DBTable<User> implements UsersColumns {


        // STATIC FIELDS
        private static final String TABLE_NAME = "users";
        private static final String TAG = UsersTable.class.getSimpleName();

        // CONSTRUCTORS
        public UsersTable() {
            super(TABLE_NAME, ALL_COLUMNS);
        }

        // ------------------------------------------------------------------------
        // METHODS
        // ------------------------------------------------------------------------
        @Override
        protected User newItem() {

            return new User();
        }

    @Override
        protected boolean onPreInsert(User user) {
            return true;
        }

        @Override
        protected User onPostInsert(User user, long _id) {
            user.setId(_id);
            return user;
        }

        @Override
        protected User onPostFindId(User user, long _id) {
            user.setId(_id);
            return user;
        }

        @Override
        protected boolean onPreDelete(User user) {
            return true;
        }

        @Override
        protected boolean onPreUpdate(User user) {
            return true;
        }

        @Override
        protected User onPostUpdate(User user) {
            return null;
        }

        @Override
        protected long getIdOf(User user) {
            return user.getId();
        }




    @Override
        public User populate(User user, Cursor cursor) {
            int iterator = 0;
            user.setId(cursor.getInt(iterator++));
            user.setUsername(cursor.getString(iterator++));
            user.setInitials(cursor.getString(iterator++));
            user.setAddress(cursor.getString(iterator));

            return user;
        }

        @Override
        public ContentValues populate(ContentValues values, User user) {

            values.put(USR,user.getUsername());
            values.put(ADDR, user.getAddress());
            values.put(INIT, user.getInitials());

            Log.d(TAG, "populate with Id: "+user.getId() + " name "+ user.getUsername());
            return values;
        }

        @Override
        protected User findItem(User user) {
            mDatabase.beginTransaction();
            User result = new User();

            try {
                String whereClause;

                if (user.getUsername()!=""&& user.getInitials() != "") {
                    //We have both columns available
                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_DOUBLE_COLUMNS,
                            USR, DatabaseUtils.sqlEscapeString(user.getUsername()),
                            INIT, DatabaseUtils.sqlEscapeString(user.getInitials()));

                } else if (user.getUsername()!="" && user.getInitials() == "") {
                    //We have just a ro word available
                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
                            USR, DatabaseUtils.sqlEscapeString(user.getUsername()));

                } else {
                    //We have only es word

                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
                            USR, DatabaseUtils.sqlEscapeString(user.getUsername()));
                }

                Cursor cursor = mDatabase.query(mTableName, null, whereClause, null, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    result = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                }
                if (cursor != null) {
                    cursor.close();
                }
                mDatabase.setTransactionSuccessful();
            } finally {
                mDatabase.endTransaction();
            }

            return result;


        }

    @Override
    protected void updateItem(User user1, User user2) {
        mDatabase.beginTransaction();

        try {
            String whereClause;

            if (user1.getId() != -1) {
                //We have the id column
                whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
                        ID, DatabaseUtils.sqlEscapeString(String.valueOf(user1.getId())));
            }

            String strFilter = "_id=" + user1.getId();
            ContentValues args = new ContentValues();
            args.put(ADDR, "new address");
            args.put(USR, "new user");
            mDatabase.update(mTableName, args, strFilter, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    @Override
        protected ArrayList<User> findItems(User user) {
            ArrayList<User> results = new ArrayList<User>();

            return results;
        }

    @Override
    protected ArrayList<User> getAllItems() {
        ArrayList<User> results = new ArrayList<User>();

            mDatabase.beginTransaction();
            try {
                Cursor cursor = mDatabase.query(mTableName, null, null, null, null, null, null, null);
                cursor.moveToFirst();
                if (cursor != null) {
                    while (!cursor.isAfterLast()) {
                        results.add(new User(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                        Log.d(TAG,cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3));
                        cursor.moveToNext();
                    }
                }
                mDatabase.setTransactionSuccessful();
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

    //***************************************************
}
