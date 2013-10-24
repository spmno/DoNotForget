package com.spmno.donotforget.data;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.dao.Dao; 
import com.j256.ormlite.table.TableUtils;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "forget.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TAG = "forget";
    private static DataBaseHelper databaseHelper;
    
	static public void initOpenHelper(Context context) {
		databaseHelper = new DataBaseHelper(context);
	}
 
	public static synchronized DataBaseHelper getInstance(){    
		return databaseHelper;           
	}
	
	Map<String, Dao> daoMaps = null;
	
	private void initDaoMaps() {
		daoMaps = new HashMap<String, Dao>();
		daoMaps.put("forgetDao", null);
		daoMaps.put("forgetItemDao", null);
	}
	
	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		initDaoMaps();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(connectionSource, Forget.class);
			TableUtils.createTable(connectionSource, ForgetItem.class);
		} catch (Exception e) {
			Log.e(TAG, "create db fail");
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		try {
			TableUtils.dropTable(connectionSource, Forget.class, true);
			TableUtils.dropTable(connectionSource, ForgetItem.class, true);
			
			onCreate(db, connectionSource);
			Log.i(TAG, "update database success");
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "update database fail");
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		super.close();
		daoMaps.clear();
	}

	public Dao<Forget, Integer> getForgetDao() {
		Dao<Forget, Integer> forgetDao = daoMaps.get("forgetDao");
		if (forgetDao == null) {
			try {
				forgetDao = getDao(Forget.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return forgetDao;
	}
	
	public Dao<ForgetItem, Integer> getForgetItemDao() {
		Dao<ForgetItem, Integer> forgetItemDao = daoMaps.get("forgetItemDao");
		if (forgetItemDao == null) {
			try {
				forgetItemDao = getDao(ForgetItem.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return forgetItemDao;
	}
}
