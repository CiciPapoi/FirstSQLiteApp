package com.example.user.firstsqliteapp.database;

/**
 * Class that contains all the constants needed for the access package.BaseColumnsBaseColumns
 * User: wraith
 */
public class AccessConstants {
    // ------------------------------------------------------------------------
    // Database constants
    // ------------------------------------------------------------------------
    /**
     * The name of the key that identify the sql statement that creates a table, in a property file.
     */
    public static final String DB_CREATE_TABLE_KEY = "table.create";
    /**
     * The name of the  key that identify the sql statement that drops a table, in a property file.
     */
    public static final String DB_DROP_TABLE_KEY = "table.drop";
    /**
     * The name of the key that identifies the number of items retrieved by the query
     */
    public static final String DB_NUMBER_OF_ITEMS_RETRIEVED = "100";

    /**
     * The name of the database file
     */
    public static final String DB_NAME = "Dictionary.db";

    /**
     * The name of the file downloaded from server and stored locally
     */
    public static final String DB_DOWNLOADED_FILE_NAME = "ro_es.db";

    /**
     * The name of the table used in the database file that can be downloaded
     */
    public static final String DB_DOWNLOADED_FILE_TABLE_NAME = "ro_es";

    /**
     * The location of the dictionary property file in the assets folder
     */
    public static final String DICTIONARY_PROPERTY_FILE = "db/dictionary.table.properties";

    /**
     * The name of the database version
     */
    public static final int DB_VERSION = 1;

    /**
     * Maximum of retries made before quiting the current job.
     */
    public static final int DB_NUMBER_OF_RETRIES = 5;

    /**
     * Number of milliseconds used to pause certain jobs, before retrying
     */
    public static final int DB_WAITING_TIME_BEFORE_RETRY = 2000;

    /**
     * Key used to identify the persistence status for the database file.
     */
    public static final String DB_DOWNLOADED_KEY = "database.downloaded.key";

    // ------------------------------------------------------------------------
    // Language related constants
    // ------------------------------------------------------------------------

    /**
     * String used to specify that the translation is made from romanian to spanish.
     */
    public static final String TRANSLATION_RO_ES = "ro_es";

    /**
     * String used to specify that the translation is made from spanish to romanian.
     */
    public static final String TRANSLATION_ES_RO = "es_ro";

    /**
     * String used as a key for persisting the translation direction in Shared Preferences
     */
    public static final String TRANSLATION_DIRECTION = "translation_direction";


    // ------------------------------------------------------------------------
    // Web access related constants
    // ------------------------------------------------------------------------
    /**
     * The address for the current server, where the search will be made.
     */
    public static final String WEB_SERVER_ADDRESS_SEARCH = "http://catalin.botezatu.com/android.php?lang=";

    /**
     * The address for the current server.
     */
    public static final String WEB_SERVER_ADDRESS = "http://catalin.botezatu.com/";

    /**
     * Separator string for multiple arguments in URL.
     * Separating the values with &, multiple variables can be passed as arguments to a hyperlink.
     */
    public static final String WEB_SEPARATOR_AND = "&";

    /**
     * Separator used to pass the search term in hyperlink as parameter.
     */
    public static final String WEB_SEPARATOR_WORD = "word=";

    /**
     * The identifier in JSON result for the word in the language used to init the search
     */
    public static final String WEB_IDENTIFIER_FROM = "from";

    /**
     * The identifier in JSON result for the to translated word
     */
    public static final String WEB_IDENTIFIER_TO = "to";

    /**
     * The name of the database from the server, where the database is archived and zipped and can be downloaded.
     */
    public static final String WEB_DATABASE_ZIP_NAME = "ro_es.zip";

    // ------------------------------------------------------------------------
    // Storage access related constants
    // ------------------------------------------------------------------------
    /**
     * The name of the directory where the database will be downloaded and stored
     */
    public static final String STORAGE_FILE_DIRECTORY_NAME = "/dictionary/";

    /**
     * The name of the file that will be downloaded and stored.
     */
    public static final String STORAGE_FILE_DATABASE_ZIP_NAME = "es_ro.zip";
}
