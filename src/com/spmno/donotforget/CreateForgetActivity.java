package com.spmno.donotforget;

import com.spmno.donotforget.adapter.ForgetCreateListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ExpandableListView;

public class CreateForgetActivity extends Activity {

	private ExpandableListView forgetExpandableListView; 
	private ForgetCreateListAdapter forgetListViewAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_forget);
		forgetExpandableListView = (ExpandableListView)findViewById(R.id.forgetExpandableListView);
		forgetListViewAdapter = new ForgetCreateListAdapter(this);
		forgetExpandableListView.setAdapter(forgetListViewAdapter);
		forgetExpandableListView.expandGroup(0);
		forgetExpandableListView.expandGroup(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_forget, menu);
		return true;
	}

}
