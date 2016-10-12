package com.whospablo.lstr.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pablo_arango on 10/11/16.
 */

public class TaskDBHelper
        extends SQLiteOpenHelper {

    private static final String DB_NAME = "tasks.db";
    private static final int DB_VERSION = 1;
    private Context mCtx;

    public TaskDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mCtx = context;
    }

    @Override
    public void onCreate( SQLiteDatabase database ) {
        TaskTable.onCreate( database );
    }



    @Override
    public void onUpgrade( SQLiteDatabase database,
                           int oldVersion,
                           int newVersion) {
        TaskTable.onUpgrade(database, oldVersion, newVersion);
    }



    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = mCtx.getFilesDir().getPath()+"/databases/" + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(Exception e){
            Log.d("Database Check:", "Database does not exit!");
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null;
    }

}
