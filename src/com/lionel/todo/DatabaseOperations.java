package com.lionel.todo;

import com.lionel.todo.TableData.TableInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOperations extends SQLiteOpenHelper {

	public static final int database_version = 2;
	public static final String CREATE_QUERY = "CREATE TABLE "
			+ TableInfo.TABLE_NAME + " (" + TableInfo.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TableInfo.TYPE + " TEXT,"
			+ TableInfo.DESCRIPTION + " TEXT," + TableInfo.MONTH + " INTEGER,"
			+ TableInfo.DAY + " INTEGER," + TableInfo.YEAR + " INTEGER );";

	public DatabaseOperations(Context context) {
		super(context, TableInfo.DATABASE_NAME, null, database_version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion < newVersion){
			System.out.println("upgrading the database");
			db.execSQL("DROP TABLE IF EXISTS " + TableInfo.TABLE_NAME);
	        onCreate(db);
		}

	}
	
	public void putInformation(DatabaseOperations dop, String type, String description, int month, int day, int year){
		
		SQLiteDatabase SQ = dop.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(TableInfo.TYPE, type);
		cv.put(TableInfo.DESCRIPTION, description);
		cv.put(TableInfo.MONTH, month);
		cv.put(TableInfo.DAY, day);
		cv.put(TableInfo.YEAR, year);
		long k = SQ.insert(TableInfo.TABLE_NAME, null, cv);
		
	}
	
	public Cursor getInformation(DatabaseOperations dop){
		
		SQLiteDatabase SQ = dop.getReadableDatabase();
		String[] columns = {TableInfo.COL_ID,TableInfo.TYPE, TableInfo.DESCRIPTION, TableInfo.MONTH, TableInfo.DAY, TableInfo.YEAR};
		Cursor resultquery = SQ.query(TableInfo.TABLE_NAME, columns, null, null, null, null, "month, day, year DESC");
		if (resultquery != null){
			resultquery.moveToFirst();
		}
		return resultquery;
		
	}

}
