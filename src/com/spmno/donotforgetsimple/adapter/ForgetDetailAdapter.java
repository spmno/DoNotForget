package com.spmno.donotforgetsimple.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spmno.donotforgetsimple.CreateForgetItemActivity;
import com.spmno.donotforgetsimple.R;
import com.spmno.donotforgetsimple.adapter.ForgetCreateListAdapter.CreateButtonListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

public class ForgetDetailAdapter extends SimpleAdapter {

	Button addForgetButton;
	private Context context;
	ArrayList<EditText> editTextList;
	
	public ForgetDetailAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
		editTextList = new ArrayList<EditText>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int lastPosition = getCount() - 1;
		if (position == lastPosition) {
			if (convertView == null) {
				convertView = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_add, null);
				addForgetButton = (Button)convertView.findViewById(R.id.addForgetItemButton);
				addForgetButton.setOnClickListener(new CreateButtonListener());
			}
			return convertView;
		} else {
			if (convertView == null) {
				convertView = super.getView(position, convertView, parent);
				convertView = (LinearLayout)convertView;
				EditText editText = (EditText)convertView.findViewById(R.id.forgetDetailTextView);
				editText.setEnabled(false);
			}
			return convertView;
		}
		//return super.getView(position, convertView, parent);
	}
	
	@Override
	public int getCount() {
		return super.getCount() + 1;
	}
	
	public void changeEditTextStatus(int postion) {
		
	}
	
	class CreateButtonListener implements OnClickListener {
		
		@Override
		public void onClick(View view) {
			//Activity activity = (Activity)context;
			Intent intent = new Intent();
			intent.putExtra("writeToDB", true);
			intent.setClass(context, CreateForgetItemActivity.class);
			context.startActivity(intent);
			//activity.startActivityForResult(intent, 1);
		}
	}
}
