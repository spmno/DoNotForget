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
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ExistForgetActivity extends Activity {

	private ListView forgetListView;
	private ExistForgetListAdapter forgetListViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exist_forget);
		forgetListView = (ListView)findViewById(R.id.forgetListView);
		forgetListView.setOnItemClickListener(new ExistItemClickListener());
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
			forgetListViewAdapter = new ExistForgetListAdapter(
					this, 
					items, 
					R.layout.list_exist_forget, 
					new String[]{"name", "time"}, 
					new int[]{R.id.forgetExistListItemTextView, R.id.forgetExistListItemTime});
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
	

	

	class ExistItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView)parent;
		    HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
		    String forgetName = map.get("name");
		    Intent intent = new Intent();
		    intent.setClass(ExistForgetActivity.this, ForgetDetailActivity.class);
		    intent.putExtra("name", forgetName);
		    startActivity(intent);
		}
		
	}
}
