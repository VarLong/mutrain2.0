package com.ytrain.mutrain.sqlite;

import android.content.Context;

public class InitDataSqlite {

	private DatabaseHelper dbOpenHelper;

	public InitDataSqlite(Context context) {
		dbOpenHelper = DatabaseHelper.Instance(context);
		while (dbOpenHelper.getWritableDatabase().isDbLockedByOtherThreads()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
