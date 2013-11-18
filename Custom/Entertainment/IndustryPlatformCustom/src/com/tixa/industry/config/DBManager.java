package com.tixa.industry.config;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
	private static int dbVersion = 4;

	public DBManager(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
	}

	public DBManager(Context context, String name) {
		this(context, name, null, dbVersion);
	}

	public DBManager(Context context, String name, int version) {
		this(context, name, null, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "create table "
				+ Constants.LOGINTABLENAME
				+ " (id integer primary key autoincrement, username text , password text, autologin varchar , savepass varchar ,info text ,current integer,authType integer);";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		try {
			if (oldVersion > newVersion) {
				String sql = " drop dabtabase " + Constants.DBNAME;
				db.execSQL(sql);
				this.onCreate(db);
			} else {
				while (oldVersion < newVersion) {
					if (oldVersion == 2) {

					} else if (oldVersion == 3) {
					} else {
						break;
					}
					oldVersion++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void closeAll(Cursor cursor, SQLiteDatabase db, DBManager dm) {
		if (cursor != null) {
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		if (dm != null) {
			dm.close();
		}
	}

}
