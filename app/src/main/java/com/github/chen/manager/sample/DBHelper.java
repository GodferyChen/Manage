package com.github.chen.manager.sample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chen on 2017/4/19.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "basev1.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String scale = "create table if not exists scale("
                + "_id integer primary key autoincrement,"
                + "user_id integer ,"
                + "device_id real ,"
                + "action_date real ,"
                + "upload integer ,"
                + "unique (user_id,action_date) ON CONFLICT REPLACE)";
        db.execSQL(scale);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists scale");
        onCreate(db);
    }
}
