package com.kin.counter;

import com.kin.counter.database.MySQLiteOpenHelper;

public class Settings {
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    public static final String ORDER_BY_NEWEST = MySQLiteOpenHelper.ID + " " + ASC;
    public static final String ORDER_BY_OLDEST = MySQLiteOpenHelper.ID + " " + DESC;
    public static final String ORDER_BY_NAME_ASC = MySQLiteOpenHelper.NAME + " COLLATE NOCASE " + ASC;
    public static final String ORDER_BY_NAME_DESC = MySQLiteOpenHelper.NAME + " COLLATE NOCASE " + DESC;
    public static final String ORDER_BY_COUNT_ASC = MySQLiteOpenHelper.COUNT + " " + ASC;
    public static final String ORDER_BY_COUNT_DESC = MySQLiteOpenHelper.COUNT + " " + DESC;
    public static final String ORDER_BY_INCREMENT_ASC = MySQLiteOpenHelper.INCREMENT + " " + ASC;
    public static final String ORDER_BY_INCREMENT_DESC = MySQLiteOpenHelper.INCREMENT + " " + DESC;

    /************************ CURRENT CONFIGURATION *************************************/
    public static String CURRENT_ORDER_BY_CONFIG = ORDER_BY_NEWEST;
    public static boolean DEBUG_MODE = true;
    /************************************************************************************/

}
