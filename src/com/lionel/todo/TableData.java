package com.lionel.todo;

import android.provider.BaseColumns;

public class TableData {
	
	public TableData(){}
	
	public static abstract class TableInfo implements BaseColumns{
		
		public static final String TYPE = "type";
		public static final String DESCRIPTION = "description";
		public static final String DAY = "day";
		public static final String MONTH = "month";
		public static final String YEAR = "year";
		public static final String DATABASE_NAME = "to_do_db";
		public static final String TABLE_NAME = "to_do_items";
		public static final String COL_ID="_id";
		
	}

}
