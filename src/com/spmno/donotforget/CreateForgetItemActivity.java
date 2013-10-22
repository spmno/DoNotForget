package com.spmno.donotforget;

import com.spmno.donotforget.data.ForgetDataCache;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_forget_item);
		forgetItemEditText = (EditText)findViewById(R.id.forgetItemEditText);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_forget_item, menu);
		return true;
	}
	
	public void completeItem(View view) {
		//ForgetDataCache forgetDataCache = ForgetDataCache.getInstance();
		String itemName = forgetItemEditText.getText().toString();
		//forgetDataCache.addForgetItem(itemName);
		Intent intent = getIntent();
		Bundle bundle = new Bundle(); 
        bundle.putString("forgetItemName", itemName);
        intent.putExtras(bundle);
        setResult(0, intent);
        finish();
	}

}
