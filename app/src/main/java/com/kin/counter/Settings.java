package com.kin.counter;

import com.kin.counter.database.MySQLiteOpenHelper;

public class Settings {
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    public static final String ORDER_BY_EARLIEST = MySQLiteOpenHelper.ID + " " + ASC;
    public static final String ORDER_BY_LATEST = MySQLiteOpenHelper.ID + " " + DESC;
    public static final String ORDER_BY_NAME_ASC = MySQLiteOpenHelper.NAME + " COLLATE NOCASE " + ASC;
    public static final String ORDER_BY_NAME_DESC = MySQLiteOpenHelper.NAME + " COLLATE NOCASE " + DESC;

    /************************ CURRENT CONFIGURATION *************************************/
    public static String CURRENT_ORDER_BY_CONFIG = ORDER_BY_EARLIEST;
    /************************************************************************************/

}
