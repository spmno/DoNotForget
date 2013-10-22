package com.spmno.donotforget;

import com.spmno.donotforget.adapter.ForgetCreateListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class CreateForgetActivity extends Activity implements OnClickListener {

	private static final int REQUESTCODE1 = 0;
	private ExpandableListView forgetExpandableListView; 
	private ForgetCreateListAdapter forgetListViewAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_forget);
		forgetExpandableListView = (ExpandableListView)findViewById(R.id.forgetExpandableListView);
		forgetListViewAdapter = new ForgetCreateListAdapter(this);
		forgetExpandableListView.setAdapter(forgetListViewAdapter);
		// do not response for group click event
		forgetExpandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;
			}
		});
		// delete group expandable icon
		forgetExpandableListView.setGroupIndicator(null);
		forgetExpandableListView.expandGroup(0);
		forgetExpandableListView.expandGroup(1);
		Button completeButton = (Button)findViewById(R.id.forgetComplete);
		completeButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_forget, menu);
		return true;
	}

	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String forgetItemName = data.getStringExtra("forgetItemName");
		forgetListViewAdapter.addForgetItem(forgetItemName);
	}
	
	@Override
	public void onClick(View view) {
		forgetListViewAdapter.saveDataToDatabase();
	}
}
