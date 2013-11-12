package com.spmno.donotforgetsimple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.spmno.donotforgetsimple.adapter.ExistForgetListAdapter;
import com.spmno.donotforgetsimple.adapter.ForgetDetailAdapter;
import com.spmno.donotforgetsimple.data.DataBaseHelper;
import com.spmno.donotforgetsimple.data.Forget;
import com.spmno.donotforgetsimple.data.ForgetDataCache;
import com.spmno.donotforgetsimple.data.ForgetItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ForgetDetailActivity extends Activity {

	private ListView forgetDetailListView;
	private SimpleAdapter forgetDetailListViewAdapter;
	private String currentForgetName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_detail);

		forgetDetailListView = (ListView)findViewById(R.id.forgetDetailListView);
		Intent intent = getIntent();
		String forgetName = intent.getStringExtra("name");
		if (!forgetName.isEmpty()) {
			currentForgetName = forgetName;
		}
		refreshList(currentForgetName);
	}
	
	private void refreshList(String forgetName) {
		setTitle(forgetName);
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<ForgetItem, Integer> forgetItemDao = databaseHelper.getForgetItemDao();
		Dao<Forget, Integer> forgetDao = databaseHelper.getForgetDao();
		try {
			Forget forget = forgetDao.queryForEq("name", forgetName).get(0);
			ForgetDataCache.getInstance().setCurrentForget(forget);
			Integer forgetId = forget.getId();
			List<ForgetItem> forgetItems = forgetItemDao.queryForEq("forgetId_id", forgetId);
			ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			for (ForgetItem forgetItem : forgetItems) {
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("name", forgetItem.getName());
				items.add(item);
			}

			forgetDetailListViewAdapter = new ForgetDetailAdapter(this, items, R.layout.list_detail_forget, new String[]{"name", "isSelect"}, new int[]{R.id.forgetDetailTextView, R.id.forgetDetailCheckBox});
			forgetDetailListView.setAdapter(forgetDetailListViewAdapter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forget_detail, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refreshList(currentForgetName);
	}

}
