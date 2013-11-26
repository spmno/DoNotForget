package com.spmno.donotforgetsimple;


import com.spmno.donotforgetsimple.data.DataBaseHelper;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
		Button deleteForgetButton = (Button)findViewById(R.id.deleteForgetButton);
		createNewForgetButton.setOnClickListener(this);
		existForgetButton.setOnClickListener(this);
		deleteForgetButton.setOnClickListener(this);
		//startService(new Intent(this, ReminderService.class));

		//CrashHandler handler = CrashHandler.getInstance();
		//handler.init(getApplicationContext());
		
		DataBaseHelper.setContext(getApplicationContext());
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
		case R.id.deleteForgetButton: {
			Intent intent = new Intent();
			intent.setClass(this, DeleteForgetActivity.class);
			startActivity(intent);
			break;
		}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
