package com.spmno.donotforgetsimple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.spmno.donotforgetsimple.data.DataBaseHelper;
import com.spmno.donotforgetsimple.data.Forget;
import com.spmno.donotforgetsimple.data.ForgetItem;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class DeleteForgetActivity extends Activity {

	private ListView forgetListView;
	private SimpleAdapter forgetListViewAdapter;
	private ArrayList<Integer> forgetIdList;
	ArrayList<Map<String, Object>> items;
	int deletePosition = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_forget);
		forgetIdList = new ArrayList<Integer>();
		forgetListView = (ListView)findViewById(R.id.forgetListView);
		forgetListView.setOnItemClickListener(new DeleteItemClickListener());
		
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<Forget, Integer> forgetDao = databaseHelper.getForgetDao();
		try {
			List<Forget> forgets = forgetDao.queryForAll();
			items = new ArrayList<Map<String, Object>>();
			for (Forget forget : forgets) {
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("name", forget.getName());
				items.add(item);
				forgetIdList.add(forget.getId());
			}
			forgetListViewAdapter = new SimpleAdapter(
					this, 
					items, 
					R.layout.list_exist_forget, 
					new String[]{"name"}, 
					new int[]{R.id.forgetExistListItemTextView});
			forgetListView.setAdapter(forgetListViewAdapter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_forget, menu);
		return true;
	}

	class DeleteItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String confirmText = getString(R.string.confirm);
			String cancelText = getString(R.string.cancel);
			String deleteHintText = getString(R.string.delete_hint);
			new AlertDialog.Builder(DeleteForgetActivity.this).setTitle(deleteHintText)   
	        .setIcon(android.R.drawable.ic_dialog_info)   
	        .setPositiveButton(confirmText, new DialogInterface.OnClickListener() {   
	       
	            @Override   
	            public void onClick(DialogInterface dialog, int which) {   
	            // 点击“确认”后的操作   
	            	deleteForget();    
	            }   
	        })   
	        .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {   
	       
	            @Override   
	            public void onClick(DialogInterface dialog, int which) {   
	            // 点击“返回”后的操作,这里不设置没有任何操作   
	            }   
	        }).show();   
	        //super.onBackPressed(); 
		}
		
	}
	
	private boolean deleteForget() {
		return deleteForget(deletePosition);
	}
	
	private boolean deleteForget(int position) {
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<Forget, Integer> forgetDao = databaseHelper.getForgetDao();
		Dao<ForgetItem, Integer> forgetItemDao = databaseHelper.getForgetItemDao();
		int forgetId = forgetIdList.get(position);
		try {
			List<ForgetItem> forgetItems = forgetItemDao.queryForEq("forgetId_id", forgetId);
			for (ForgetItem forgetItem : forgetItems) {
				forgetItemDao.deleteById(forgetItem.getId());
			}
			forgetDao.deleteById(forgetId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
