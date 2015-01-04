package com.lionel.todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.lionel.todo.TableData.TableInfo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ToDoList extends Activity {
	
	static final int dialog_id = 1;
	int yr, day, month;
	private Button btn;
	private ListView lv;
	private EditText edt;
	private String type;
	private String description;
	private String _date="DATE";
	private Context ctx = this;
	DatabaseOperations DB = new DatabaseOperations(ctx);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		type = intent.getStringExtra(MainActivity.TYPE);
		
		setContentView(R.layout.list_view);
		Calendar today = Calendar.getInstance();
		yr = today.get(Calendar.YEAR);
		day = today.get(Calendar.DAY_OF_MONTH);
		month = today.get(Calendar.MONTH);
		btn = (Button) findViewById(R.id.button1);
		lv = (ListView) findViewById(R.id.listView1);
		edt = (EditText) findViewById(R.id.textView_date);

		//strArry = new ArrayList<String>();
		//adapter = new ArrayAdapter<String>(getApplicationContext(),
		//		android.R.layout.simple_list_item_1, strArry);
		//lv.setAdapter(adapter);
		populatelistview();
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// add new data then load existing data from db and display list
				
				description = edt.getText().toString();
				//strArry.add(description);
				showDialog(dialog_id);
	
				//adapter.notifyDataSetChanged();
				
			}
		});

	}
	
	protected Dialog onCreateDialog(int id){
		
		switch(id){
		case dialog_id:
			return new DatePickerDialog(this, mDateSetListener, yr, month, day);
		}
		return null;
		
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			
			boolean display_list = false;
			display_list = saveDataToDB(monthOfYear, dayOfMonth, year);
			if (display_list){
				//show list
				populatelistview();
			}
			
		}
	};

	protected boolean saveDataToDB(int month, int day, int year) {
		
		//DatabaseOperations DB = new DatabaseOperations(ctx);
		DB.putInformation(DB, type, description, month, day, year);
		return true;
		
	}

	protected void populatelistview() {
		// TODO Auto-generated method stub
		Cursor cursor = DB.getInformation(DB);
		DatabaseUtils.dumpCursor(cursor);
		String[] columnNames = new String[] {TableInfo.DESCRIPTION,TableInfo.DAY};
		int[] listdetails = new int[] {R.id.textView_task, R.id.textView_date};
		SimpleCursorAdapter myListAdapter;
		myListAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_layout, cursor, columnNames, listdetails, 0);
		myListAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				// TODO Auto-generated method stub
				if(columnIndex==cursor.getColumnIndex(TableInfo.DAY)){
					System.out.println("inside view binder hello");
					int day = cursor.getColumnIndex(TableInfo.DAY);
					int month= cursor.getColumnIndex(TableInfo.MONTH);
					int year = cursor.getColumnIndex(TableInfo.YEAR);
					
					if (day!= -1 && month != -1 && year != -1){
						GregorianCalendar date = new GregorianCalendar(cursor.getInt(year), cursor.getInt(month), cursor.getInt(day));
						//SimpleDateFormat format = new SimpleDateFormat("MM/")
						java.text.DateFormat formatter = android.text.format.DateFormat.getDateFormat(getApplicationContext());
						String date_string = formatter.format(date.getTime());
						((TextView) view).setText(date_string);
						return true;
					}
				}
				return false;
			}
		});
		
		lv.setAdapter(myListAdapter);
	}
	
}
