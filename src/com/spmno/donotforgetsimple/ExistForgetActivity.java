package com.spmno.donotforgetsimple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.spmno.donotforgetsimple.adapter.ExistForgetListAdapter;
import com.spmno.donotforgetsimple.data.DataBaseHelper;
import com.spmno.donotforgetsimple.data.Forget;
import com.spmno.donotforgetsimple.data.ForgetItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ExistForgetActivity extends Activity implements OnDoubleTapListener, OnTouchListener, OnGestureListener{

	private ListView forgetListView;
	private ExistForgetListAdapter forgetListViewAdapter;
	private ArrayList<Integer> forgetIdList;
	ArrayList<Map<String, Object>> items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exist_forget);
		forgetIdList = new ArrayList<Integer>();
		forgetListView = (ListView)findViewById(R.id.forgetListView);
		forgetListView.setOnItemClickListener(new ExistItemClickListener());
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
			forgetListViewAdapter = new ExistForgetListAdapter(
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




	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		int e1X = (int)e1.getX();
		int e1Y = (int)e1.getY();
		int e2X = (int)e2.getX();
		int e2Y = (int)e2.getY();
		int e1Position = forgetListView.pointToPosition((int)e1X, (int)e1Y);
		int e2Position = forgetListView.pointToPosition((int)e2X, (int)e2Y);
		if (e1Position == e2Position) {
			String deleteNote = this.getString(R.string.delete_success);
			Toast.makeText(this, deleteNote, Toast.LENGTH_SHORT).show();
			deleteForget(e1Position);
			items.remove(e1Position);
			forgetListViewAdapter.notifyDataSetChanged();
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean deleteForget(int position) {
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<Forget, Integer> forgetDao = databaseHelper.getForgetDao();
		Dao<ForgetItem, Integer> forgetItemDao = databaseHelper.getForgetItemDao();
		int forgetId = forgetIdList.get(position);
		try {
			List<ForgetItem> forgetItems = forgetItemDao.queryForEq("forgetId", forgetId);
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
