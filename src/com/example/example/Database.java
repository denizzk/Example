package com.example.example;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Database extends SQLiteOpenHelper {

	public static final String DBName = "ImagesDB";
	public static final int version = 1;

	public Database(Context context) {
		super(context, DBName, null, version);
	}

	public void queryData(String sql) {

		SQLiteDatabase database = getWritableDatabase();
		database.execSQL(sql);
	}

	public void insertData(String name, String date, byte[] image) {


		if (getData("SELECT NAME FROM IMAGES WHERE NAME='" + name + "'").getCount() == 0) {
			SQLiteDatabase database = getWritableDatabase();
			String sql = "INSERT INTO IMAGES VALUES (null, ?, ?, ?)";

			SQLiteStatement statement = database.compileStatement(sql);
			statement.clearBindings();

			statement.bindString(1, name);
			statement.bindString(2, date);
			statement.bindBlob(3, image);

			statement.executeInsert();

			database.execSQL("CREATE TABLE IF NOT EXISTS '" + name
					+ "' (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT, image BLOB)");

			sql = "INSERT INTO '" + name + "' VALUES (null, ?, ?, ?)";

			statement = database.compileStatement(sql);
			statement.clearBindings();

			statement.bindString(1, name);
			statement.bindString(2, date);
			statement.bindBlob(3, image);

			statement.executeInsert();
		} else {
			SQLiteDatabase database = getWritableDatabase();
			String sql = "INSERT INTO '"+name+"' VALUES (null, ?, ?, ?)";

			SQLiteStatement statement = database.compileStatement(sql);
			statement.clearBindings();

			statement.bindString(1, name);
			statement.bindString(2, date);
			statement.bindBlob(3, image);

			statement.executeInsert();
		}
	}

	public Cursor getData(String sql) {

		SQLiteDatabase database = getReadableDatabase();
		return database.rawQuery(sql, null);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
