package com.spmno.donotforget;

import com.spmno.donotforget.data.DataBaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button createNewForgetButton = (Button)findViewById(R.id.addForgetButton);
		Button existForgetButton = (Button)findViewById(R.id.existForgetButton);
		
		createNewForgetButton.setOnClickListener(this);
		existForgetButton.setOnClickListener(this);
		DataBaseHelper.initOpenHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.addForgetButton: {
			Intent intent = new Intent();
			intent.setClass(this, CreateForgetActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.existForgetButton: {
			Intent intent = new Intent();
			intent.setClass(this, ExistForgetActivity.class);
			startActivity(intent);
			break;
		}
		}
	}
}
