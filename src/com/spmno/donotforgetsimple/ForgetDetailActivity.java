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
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ForgetDetailActivity extends Activity implements OnDoubleTapListener, OnTouchListener, OnGestureListener  {

	private ListView forgetDetailListView;
	private SimpleAdapter forgetDetailListViewAdapter;
	private String currentForgetName;
	private GestureDetector listDetector;
	private ArrayList<Integer> forgetItemIdList;
	ArrayList<Map<String, Object>> items;
	
	
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
		listDetector = new GestureDetector(this, this);
		listDetector.setOnDoubleTapListener(this);
		refreshList(currentForgetName);
	}
	
	private void refreshList(String forgetName) {
		setTitle(forgetName);
		forgetItemIdList = new ArrayList<Integer>();
		items = new ArrayList<Map<String, Object>>();
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<ForgetItem, Integer> forgetItemDao = databaseHelper.getForgetItemDao();
		Dao<Forget, Integer> forgetDao = databaseHelper.getForgetDao();
		try {
			Forget forget = forgetDao.queryForEq("name", forgetName).get(0);
			ForgetDataCache.getInstance().setCurrentForget(forget);
			Integer forgetId = forget.getId();
			List<ForgetItem> forgetItems = forgetItemDao.queryForEq("forgetId_id", forgetId);
			
			for (ForgetItem forgetItem : forgetItems) {
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("name", forgetItem.getName());
				items.add(item);
				forgetItemIdList.add(forgetItem.getId());
			}

			forgetDetailListViewAdapter = new ForgetDetailAdapter(this, items, R.layout.list_detail_forget, new String[]{"name", "isSelect"}, new int[]{R.id.forgetDetailTextView, R.id.forgetDetailCheckBox});
			forgetDetailListView.setAdapter(forgetDetailListViewAdapter);
			forgetDetailListView.setOnTouchListener(this);
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
	
	@Override  
    public boolean onTouch(View v, MotionEvent event) {  
        // TODO Auto-generated method stub  
        listDetector.onTouchEvent(event);  
        return false;  
    }

	//OnGestureListener method
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
		int e1Position = forgetDetailListView.pointToPosition((int)e1X, (int)e1Y);
		int e2Position = forgetDetailListView.pointToPosition((int)e2X, (int)e2Y);
		if (e1Position == e2Position) {
			String deleteNote = this.getString(R.string.delete_success);
			Toast.makeText(this, deleteNote, Toast.LENGTH_SHORT).show();
			deleteForgetItem(e1Position);
			items.remove(e1Position);
			forgetDetailListViewAdapter.notifyDataSetChanged();
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
	public boolean onDoubleTap(MotionEvent e) {
		int x = (int)e.getX();
		int y = (int)e.getY();
		int position = forgetDetailListView.pointToPosition((int)x, (int)y);
		((ForgetDetailAdapter) forgetDetailListViewAdapter).changeEditTextStatus(position);
		return true;
	}
	
	private boolean deleteForgetItem(int position) {
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<ForgetItem, Integer> forgetItemDao = databaseHelper.getForgetItemDao();
		try {
			forgetItemDao.deleteById(forgetItemIdList.get(position));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
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

}
