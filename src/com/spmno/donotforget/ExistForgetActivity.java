package com.spmno.donotforget;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ExistForgetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exist_forget);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exist_forget, menu);
		return true;
	}

}
