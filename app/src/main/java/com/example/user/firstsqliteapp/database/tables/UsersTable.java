package com.example.user.firstsqliteapp.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.text.TextUtils;

import com.example.user.firstsqliteapp.data.User;
import com.example.user.firstsqliteapp.database.DBTable;
import com.example.user.firstsqliteapp.database.UsersColumns;

import java.util.ArrayList;

/**
 * This class handles basic operations on the users table
 */
public class UsersTable extends DBTable<User> implements UsersColumns {
        // STATIC FIELDS
        private static final String TABLE_NAME = "ro_es";

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
            return values;
        }

        @Override
        protected User findItem(User user) {
            User result = new User();
            /*
            mDatabase.beginTransaction();

            try {
                String whereClause;

                if (user.getUsername().length() > 0 && user.setInitials().length() > 0) {
                    //We have both columns available
                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_DOUBLE_COLUMNS,
                            RO_WORD, DatabaseUtils.sqlEscapeString(user.getRoWord()),
                            ES_WORD, DatabaseUtils.sqlEscapeString(user.getEsWord()));

                } else if (user.getRoWord().length() > 0 && user.getEsWord().length() == 0) {
                    //We have just a ro word available
                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
                            RO_WORD, DatabaseUtils.sqlEscapeString(user.getRoWord()));

                } else {
                    //We have only es word
                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN,
                            ES_WORD, DatabaseUtils.sqlEscapeString(user.getEsWord()));
                }


                Cursor cursor = mDatabase.query(mTableName, null, whereClause, null, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    result = new Dictionary(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                }
                if (cursor != null) {
                    cursor.close();
                }
                mDatabase.setTransactionSuccessful();
            } finally {
                mDatabase.endTransaction();
            }
            */
            return result;
        }


    @Override
        protected ArrayList<User> findItems(User user) {
            ArrayList<User> results = new ArrayList<User>();
            /*
            mDatabase.beginTransaction();

            try {
                String whereClause;

                if (user.getEsWord().length() > 0 && user.getRoWord().length() > 0) {
                    //We have both columns available
                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_LIKE_DOUBLE_COLUMNS,
                            RO_WORD, DatabaseUtils.sqlEscapeString(user.getRoWord() + "%"),
                            ES_WORD, DatabaseUtils.sqlEscapeString(user.getEsWord() + "%"));

                } else if (user.getRoWord().length() > 0 && user.getEsWord().length() == 0) {
                    //We have just a ro word available
                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_LIKE_SINGLE_COLUMN,
                            RO_WORD, DatabaseUtils.sqlEscapeString(user.getRoWord() + "%"));

                } else {
                    //We have only es word
                    whereClause = String.format(TextUtils.FORMAT_STRING_WHERE_CLAUSE_LIKE_SINGLE_COLUMN,
                            ES_WORD, DatabaseUtils.sqlEscapeString(user.getEsWord() + "%"));
                }


                Cursor cursor = mDatabase.query(mTableName, null, whereClause, null, null, null, null, AccessConstants.DB_NUMBER_OF_ITEMS_RETRIEVED);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        results.add(new Dictionary(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                    }
                }


                mDatabase.setTransactionSuccessful();
            } finally {
                mDatabase.endTransaction();
            }
        */
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
