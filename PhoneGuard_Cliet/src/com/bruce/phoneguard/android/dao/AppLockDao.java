package com.bruce.phoneguard.android.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.bruce.phoneguard.android.db.AppLockDBHelper;

import java.util.ArrayList;
import java.util.List;

public class AppLockDao {
    private Context context;
	private AppLockDBHelper dbHelper;

	public AppLockDao(Context context) {
		this.context = context;
		dbHelper = new AppLockDBHelper(context);
	}

	/**
	 * 查询
	 */
	public boolean find(String packname) {
		boolean result = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select packname from applock where packname=?",
					new String[] { packname });
			if (cursor.moveToNext()) {
				result = true;
			}
			cursor.close();

			db.close();
		}
		return result;
	}

	/**
	 * 添加
	 */
	public void add(String packname){
		if(find(packname)){
			return ;
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("insert into applock (packname) values (?)", new Object[]{packname});
			db.close();
		}
	}
	/**
	 * 删除
	 */
	public void delete(String packname){

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("delete from applock where packname=?", new Object[]{packname});
			db.close();
		}
	}
	

	
	
	/**
	 * 查找全部包名
	 */
	public List<String> getAllApps(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<String> packnames = new ArrayList<String>();
		if (db.isOpen()) {
		  Cursor cursor =	db.rawQuery("select packname from applock", null);
			while (cursor.moveToNext()) {
				String packname = cursor.getString(0);
				packnames.add(packname);
			}
			cursor.close();
			db.close();
		}
		return packnames;
	}
}
