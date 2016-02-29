package com.nucyzh.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author:XiYang on 2016/2/18.
 * Email:765849854@qq.com
 *
 * 实现SQLiteOpenHelper接口的NotesDB类，用于创建数据库表
 */
public class NotesDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME_NOTES = "notes";
    public static final String TABLE_NAME_MEDIA = "media";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_NOTE_NAME = "name";
    public static final String COLUMN_NAME_NOTE_CONTENT = "content";
    public static final String COLUMN_NAME_NOTE_DATE = "date";
    public static final String COLUMN_NAME_MEDIA_PATH = "path";
    public static final String COLUMN_NAME_MEDIA_OWNER_NOTE_ID = "note_id";


    public NotesDB(Context context) {
        super(context, "notes", null, 1);
    }
    /**
     * 当第一次打开数据库，表不存在时调用，以创建表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE " + TABLE_NAME_NOTES + "("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_NOTE_NAME + " TEXT NOT NULL DEFAULT \"\","
                + COLUMN_NAME_NOTE_CONTENT + " TEXT NOT NULL DEFAULT \"\","
                + COLUMN_NAME_NOTE_DATE + " TEXT NOT NULL DEFAULT \"\"" + ")");
        System.out.println("创建notes");
        db.execSQL("CREATE TABLE " + TABLE_NAME_MEDIA + "("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_MEDIA_PATH + " TEXT NOT NULL DEFAULT \"\","
                + COLUMN_NAME_MEDIA_OWNER_NOTE_ID + " INTEGER NOT NULL DEFAULT 0"
                + ")");
        System.out.println("创建media");
    }

    /**
     * 当表存在且是旧版本时调用，删除旧表，创建新表
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
