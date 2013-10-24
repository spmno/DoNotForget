package com.spmno.donotforget;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.spmno.donotforget.adapter.ExistForgetListAdapter;
import com.spmno.donotforget.data.DataBaseHelper;
import com.spmno.donotforget.data.Forget;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class ExistForgetActivity extends Activity {

	private ListView forgetListView;
	private ExistForgetListAdapter forgetListViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exist_forget);
		forgetListView = (ListView)findViewById(R.id.forgetListView);
		
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<Forget, Integer> forgetDao = databaseHelper.getForgetDao();
		try {
			List<Forget> forgets = forgetDao.queryForAll();
			ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			for (Forget forget : forgets) {
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("name", forget.getName());
				items.add(item);
			}
			forgetListViewAdapter = new ExistForgetListAdapter(this, items, R.layout.list_exist_forget, new String[]{"name"}, new int[]{R.id.forgetExistListItemTextView});
			forgetListView.setAdapter(forgetListViewAdapter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exist_forget, menu);
		return true;
	}
	


}
