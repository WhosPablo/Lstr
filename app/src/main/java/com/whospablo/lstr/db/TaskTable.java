package com.whospablo.lstr.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by pablo_arango on 10/11/16.
 */

public class TaskTable {

    // Column names
    public static final String TABLE_NAME = "Task";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_SUMMARY = "Summary";
    public static final String COLUMN_FINISHED = "Finished";

    // SQL statement to createTask the table
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_SUMMARY + " TEXT NOT NULL,"
            + COLUMN_FINISHED + " INTEGER DEFAULT 0"
            + ");";

    private static HashSet<String> VALID_COLUMN_NAMES;

    static {
        String[] validNames = {
                TABLE_NAME,
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_SUMMARY,
                COLUMN_FINISHED
        };
        VALID_COLUMN_NAMES = new HashSet<>(Arrays.asList(validNames));
    }


    static void onCreate( SQLiteDatabase database ) {

        database.execSQL( DATABASE_CREATE );

    }

    static void onUpgrade( SQLiteDatabase database,
                                  int oldVersion,
                                  int newVersion) {
        Log.d(TaskTable.class.getName(),
                "Upgrading database from version "
                        + oldVersion + " to " + newVersion
                        + ", which destroyed all existing data");

        database.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( database );

        Log.d("TableTask.onUpgrade()", "complete");
    }

    public static void validateProjection(String[] projection) {

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));

            // check if all columns which are requested are available
            if ( !VALID_COLUMN_NAMES.containsAll( requestedColumns ) ) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }


}
