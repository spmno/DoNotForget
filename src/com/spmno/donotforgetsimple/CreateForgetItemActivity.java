package com.spmno.donotforgetsimple;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.spmno.donotforgetsimple.data.DataBaseHelper;
import com.spmno.donotforgetsimple.data.Forget;
import com.spmno.donotforgetsimple.data.ForgetDataCache;
import com.spmno.donotforgetsimple.data.ForgetItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateForgetItemActivity extends Activity {

	private EditText forgetItemEditText;
	private Button completeButton;
	private boolean isWriteImmediately;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_forget_item);
		forgetItemEditText = (EditText)findViewById(R.id.forgetItemEditText);
		Intent intent = getIntent();
		isWriteImmediately = intent.getBooleanExtra("writeToDB", false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_forget_item, menu);
		return true;
	}
	
	public void completeItem(View view) {
		String itemName = forgetItemEditText.getText().toString();
		//ForgetDataCache forgetDataCache = ForgetDataCache.getInstance();
		if (isWriteImmediately) {
			DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
			Dao<ForgetItem, Integer>forgetItemDao = databaseHelper.getForgetItemDao();
			ForgetItem forgetItem = new ForgetItem();
			forgetItem.setName(itemName);
			Forget forget = ForgetDataCache.getInstance().getCurrentForget();
			forgetItem.setForgetId(forget);
			try {
				forgetItemDao.create(forgetItem);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//forgetDataCache.addForgetItem(itemName);
			Intent intent = getIntent();
			Bundle bundle = new Bundle(); 
	        bundle.putString("forgetItemName", itemName);
	        intent.putExtras(bundle);
	        setResult(1, intent);
		}
		
        finish();
	}

}
