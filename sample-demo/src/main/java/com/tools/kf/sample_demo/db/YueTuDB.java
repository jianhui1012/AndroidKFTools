package com.tools.kf.sample_demo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tools.kf.sample_demo.model.DLPic;
import com.tools.kf.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
public class YueTuDB {

    //数据库名称
    public static final String DB_NAME = "yuetu";
    //数据库版本
    public static final int VERSION = 1;

    private static YueTuDB yueTuDB;

    private SQLiteDatabase db;

    /***
     * 私有构造方法
     *
     * @param context
     */
    private YueTuDB(Context context) {
        YueTuOpenHelper yueTuOpenHelper = new YueTuOpenHelper(context, DB_NAME, null, VERSION);
        db = yueTuOpenHelper.getWritableDatabase();
    }

    /***
     * 获取YueTuDB实例
     *
     * @param context
     * @return
     */
    public synchronized static YueTuDB getInstance(Context context) {
        if (yueTuDB == null) {
            yueTuDB = new YueTuDB(context);
        }
        return yueTuDB;
    }

    public boolean insertDLPic(DLPic dlPic) {
        boolean isok = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("originurl", dlPic.getOriginurl());
            contentValues.put("filename", dlPic.getFilename());
            contentValues.put("filepath", dlPic.getFilepath());
            contentValues.put("islike", dlPic.islike());
            db.insert("DLPic", null, contentValues);
            isok = true;
        } catch (Exception ex) {
            LogHelper.LogD(ex.toString());
        }
        return isok;
    }

    public boolean isCanDownLoad(String originurl) {
        boolean isok = true;
        try {
            Cursor cursor = db.query("DLPic", null, "originurl=?", new String[]{String.valueOf(originurl)}, null, null, null);
            if (commonSearch(cursor).size() == 1) {
                isok = false;
            }
        } catch (Exception ex) {
            LogHelper.LogD(ex.toString());
        }
        return isok;
    }

    public List<DLPic> loadDLPic() {
        Cursor cursor = db.query("DLPic", null, null, null, null, null, null);
        return commonSearch(cursor);
    }

    public boolean deleteDLPic(int id) {
        return db.delete("DLPic", "id=?", new String[]{String.valueOf(id)}) > 0 ? true : false;
    }

    private List<DLPic> commonSearch(Cursor cursor) {
        List<DLPic> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                DLPic dlPic = new DLPic();
                dlPic.setId(cursor.getInt(cursor.getColumnIndex("id")));
                dlPic.setFilename(cursor.getString(cursor.getColumnIndex("filename")));
                dlPic.setFilepath(cursor.getString(cursor.getColumnIndex("filepath")));
                dlPic.setOriginurl(cursor.getString(cursor.getColumnIndex("originurl")));
                dlPic.setIslike(cursor.getInt(cursor.getColumnIndex("islike")) == 0 ? false : true);
                list.add(dlPic);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        return list;
    }
}
