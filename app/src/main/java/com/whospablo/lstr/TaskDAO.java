package com.whospablo.lstr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.whospablo.lstr.db.TaskDBHelper;
import com.whospablo.lstr.db.TaskTable;

import java.util.ArrayList;

/**
 * Created by pablo_arango on 10/11/16.
 */

class TaskDAO {
    // Db fields
    private SQLiteDatabase database;
    private TaskDBHelper dbHelper;
    private String[] tableColumns = {
            TaskTable.COLUMN_ID,
            TaskTable.COLUMN_TITLE,
            TaskTable.COLUMN_SUMMARY,
            TaskTable.COLUMN_FINISHED
    };

    TaskDAO(Context context) { dbHelper = new TaskDBHelper( context ); }

    void open(){
        if(database == null) {
            database = dbHelper.getWritableDatabase();
        }
    }

    void close(){
        dbHelper.close();
        database = null;
    }

    Task createTask(String title, String summary, boolean finished){
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_TITLE, title);
        values.put(TaskTable.COLUMN_SUMMARY, summary);
        values.put(TaskTable.COLUMN_FINISHED, finished? 1:0);

        long id = database.insert(TaskTable.TABLE_NAME, null, values);

        Cursor cursor = database.query(TaskTable.TABLE_NAME, tableColumns,
                TaskTable.COLUMN_ID + " = " + id, null, null, null, null);

        cursor.moveToFirst();

        Task t = cursorToTask(cursor);
        cursor.close();

        return t;

    }

    int saveTask(Task t) {
        long id = t.getId();
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_TITLE, t.getTitle());
        values.put(TaskTable.COLUMN_SUMMARY, t.getSummary());
        values.put(TaskTable.COLUMN_FINISHED, t.getFinished()? 1:0);

        return database.update(TaskTable.TABLE_NAME,values,
                TaskTable.COLUMN_ID + " = " + id, null);
    }

    int deleteTask(Task t) {
        long id = t.getId();
        return database.delete(TaskTable.TABLE_NAME, TaskTable.COLUMN_ID + " = " + id, null);
    }

    ArrayList<Task> getAllTasks(){
        ArrayList<Task> tasks =  new ArrayList<>();

        Cursor cursor = database.query(TaskTable.TABLE_NAME, tableColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Task t = cursorToTask(cursor);
            tasks.add(t);
            cursor.moveToNext();
        }
        cursor.close();

        return tasks;
    }

    private Task cursorToTask( Cursor c ){
        long id = c.getLong(0);
        String title = c.getString(1);
        String summary = c.getString(2);
        int finished = c.getInt(3);
        return new Task(id, title, summary, finished == 1);
    }


}
