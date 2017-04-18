package com.tools.kf.sample_demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
public class YueTuOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_DLPIC = "create table DLPic (" +
            "id integer primary key autoincrement," +
            "originurl text ," +
            "filename text ," +
            "filepath text ," +
            "islike integer null" +
            ")";

    public YueTuOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DLPIC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
