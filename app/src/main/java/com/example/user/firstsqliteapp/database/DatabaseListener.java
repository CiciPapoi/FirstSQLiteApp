package com.example.user.firstsqliteapp.database;

/**
 * Interface containing methods used in database lifecycle.
 */
public interface DatabaseListener {
    void onBeforeDatabaseCreate(ApplicationDB db);
    void onAfterDatabaseCreate(ApplicationDB db);

    void onBeforeDatabaseUpgrade(ApplicationDB db, int oldVersion, int newVersion);
    void onAfterDatabaseUpgrade(ApplicationDB db, int oldVersion, int newVersion);

    void onDatabaseOpened(ApplicationDB db);
    void onDatabaseClosed(ApplicationDB db);
}
