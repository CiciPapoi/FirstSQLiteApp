package com.example.user.firstsqliteapp.utils;

/**
 * Class containg string formatting and different utilities.
 * User: Cici
 */
public class TextUtils {
    /**
     * String format string = decimal values.
     */
    public static final String FORMAT_STRING_EQUAL_DEC = "%s = %d";

    /**
     * String format used to create a where clause for both columns in dictionary table
     */
    public static  String FORMAT_STRING_WHERE_CLAUSE_DOUBLE_COLUMNS = "%s=%s and %s=%s";

    /**
     * String format used to create a where...LIKE clause for both columns in dictionary table
     */
    public static  String FORMAT_STRING_WHERE_CLAUSE_LIKE_DOUBLE_COLUMNS = "%s LIKE %s and %s LIKE %s";

    /**
     * String format used to create a where clause when just a single column is present
     */
    public static  String FORMAT_STRING_WHERE_CLAUSE_SINGLE_COLUMN = "%s=%s";

    /**
     * String format used to create a where...LIKE clause when just a single column is present
     */
    public static  String FORMAT_STRING_WHERE_CLAUSE_LIKE_SINGLE_COLUMN = "%s LIKE %s";

    /**
     * String containing the double side arrow symbol unicode
     */
    public static final String FORMAT_DOUBLE_SIDE_ARROW_SYMBOL =" \u21cb ";

    /**
     * Method used to change the underline with double side arrow symbol
     */
    public static String decorateWithArrowCharacter(String string){
        return string.replace("_","\u21cb");
    }

}
