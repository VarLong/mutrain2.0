package com.ytrain.mutrain.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String dbname = "mutrain";
	private static int version = 1;
	private static DatabaseHelper instance;

	private DatabaseHelper(Context context) {
		super(context, dbname, null, version);
	}

	// 变成全局单例
	public static DatabaseHelper Instance(Context context) {
		synchronized (DatabaseHelper.class) {
			if (instance == null) {
				instance = new DatabaseHelper(context);
			}
		}
		return instance;
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// 课程分类
			db.execSQL("CREATE TABLE IF NOT EXISTS clazz(id varchar(100),level varchar(10),name varchar(200))");
			// 推荐课程
			db.execSQL("CREATE TABLE IF NOT EXISTS indexcourses(id varchar(200),vtype int,hits int,img_url varchar(300),lector varchar(100),max_type varchar(100),name varchar(100),source varchar(100),stars double,video_url varchar(400))");
			// 分类课程
			db.execSQL("CREATE TABLE IF NOT EXISTS courses(id varchar(200),hits int,img_url varchar(300),lector varchar(100),max_type varchar(100),name varchar(100),source varchar(100),stars double,video_url varchar(400))");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
