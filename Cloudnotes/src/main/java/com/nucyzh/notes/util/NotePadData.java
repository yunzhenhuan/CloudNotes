package com.nucyzh.notes.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotePadData extends SQLiteOpenHelper {

	public static final String TABLE_NAME_NOTES = "notes";
	public static final String TABLE_NAME_MEDIA = "media";
	public static final String COLUMN_NAME_ID = "_id";
	public static final String COLUMN_NAME_NOTE_NAME = "name";
	public static final String COLUMN_NAME_NOTE_CONTENT = "content";
	public static final String COLUMN_NAME_NOTE_DATE = "date";
	public static final String COLUMN_NAME_MEDIA_PATH = "path";
	public static final String COLUMN_NAME_MEDIA_OWNER_NOTE_ID = "note_id";

	    // public static final String TABLE_NAME = "notes";
	    // public static final String TIME="date";
	    // public static final String TITLE = "title";
	     // public static final String ID="id";
	    // public static final String MAIN="contentDetail";
     private static final String DATABASE_NAME="notes.db";
     private static final int DATABASE_VERSION=1;


	public NotePadData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建两张表
	 * @param db
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME_NOTES + "(" + COLUMN_NAME_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_NOTE_NAME
				+ " TEXT NOT NULL DEFAULT \"\"," + COLUMN_NAME_NOTE_CONTENT
				+ " TEXT NOT NULL DEFAULT \"\"," + COLUMN_NAME_NOTE_DATE
				+ " TEXT NOT NULL DEFAULT \"\"" + ")");
		db.execSQL("CREATE TABLE " + TABLE_NAME_MEDIA + "(" + COLUMN_NAME_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_NAME_MEDIA_PATH + " TEXT NOT NULL DEFAULT \"\","
				+ COLUMN_NAME_MEDIA_OWNER_NOTE_ID
				+ " INTEGER NOT NULL DEFAULT 0" + ")");
		//String detailQql="create table notes(_id  INTEGER primary key autoincrement,date text not null, name text not null,content text not null)";
		//db.execSQL(detailQql);
	}

	/**
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
