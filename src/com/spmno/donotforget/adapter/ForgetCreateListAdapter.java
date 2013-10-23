package com.spmno.donotforget.adapter;

import java.sql.SQLException;
import java.util.ArrayList;

import com.j256.ormlite.dao.Dao;
import com.spmno.donotforget.CreateForgetItemActivity;
import com.spmno.donotforget.R;
import com.spmno.donotforget.data.DataBaseHelper;
import com.spmno.donotforget.data.Forget;
import com.spmno.donotforget.data.ForgetItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class ForgetCreateListAdapter extends BaseExpandableListAdapter {

	private String[] group = {"…Ë÷√", "ŒÔ∆∑List" };
	private ArrayList<String> forgetItemArrayList;
	private int[] childCount = {3, 1};
	private Activity context;
	
	// useful control
	LinearLayout timePickerLinearLayout;
	LinearLayout placeLinearLayout;
	LinearLayout nameLinearLayout;
	TimePicker remindTimePicker;
	EditText remindPlaceNameEditText;
	EditText remindNameEditText;
	Button addForgetButton;
	
	public ForgetCreateListAdapter(Activity context) {
		this.context = context;
		forgetItemArrayList = new ArrayList<String>();
		initAdapterView();
	}
	
	public void initAdapterView() {
		//time
		timePickerLinearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_time, null);
		TextView remindTimeTitle = (TextView)timePickerLinearLayout.findViewById(R.id.remindTextView);
		ImageView remindTimeIcon = (ImageView)timePickerLinearLayout.findViewById(R.id.remindImageView);
		remindTimePicker = (TimePicker)timePickerLinearLayout.findViewById(R.id.remindTimePicker);
		remindTimeTitle.setText(context.getString(R.string.time));
		
		//place
		placeLinearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_place, null);
		TextView remindPlaceTitle = (TextView)placeLinearLayout.findViewById(R.id.placeTitleTextView);
		ImageView remindPlaceIcon = (ImageView)placeLinearLayout.findViewById(R.id.placeTitleImageView);
		remindPlaceNameEditText = (EditText)placeLinearLayout.findViewById(R.id.placeName);
		
		//name
		nameLinearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_event, null);
		TextView remindEventTitle = (TextView)nameLinearLayout.findViewById(R.id.eventTitleTextView);
		remindNameEditText = (EditText)nameLinearLayout.findViewById(R.id.forgetNameTextView);
		
		//add button
		addForgetButton = new Button(context);
		String addText = context.getString(R.string.add_forget);
		addForgetButton.setText(addText);
		addForgetButton.setOnClickListener(new CreateButtonListener());
	}
	
	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (groupPosition == 0) {
			if (childPosition == 0) {

				//linearLayout.addView(remindTimeTitle);
				//linearLayout.addView(remindTimeIcon);
				//linearLayout.addView(remindTimer);
				return timePickerLinearLayout;
			} else if (childPosition == 1) {
				//linearLayout.addView(remindPlaceTitle);
				//linearLayout.addView(remindPlaceIcon);
				//linearLayout.addView(remindPlaceName);
				return placeLinearLayout;
			} else if (childPosition == 2) {
				
				//Button businessButton = (Button)linearLayout.findViewById(R.id.businessButton);
				//Button tripButton = (Button)linearLayout.findViewById(R.id.tripButton);
				//Button otherButton = (Button)linearLayout.findViewById(R.id.otherButton);
				//linearLayout.addView(remindEventTitle);
				//linearLayout.addView(businessButton);
				//linearLayout.addView(tripButton);
				//linearLayout.addView(otherButton);
				return nameLinearLayout;
			}
			
		} else if (groupPosition == 1) {
			if (childPosition == forgetItemArrayList.size()) {
				return addForgetButton;
			} else {
				TextView forgetItemTextView = new TextView(context);
				String forgetItemName = forgetItemArrayList.get(childPosition);
				forgetItemTextView.setText(forgetItemName);
				return forgetItemTextView;
			}

		}
		
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childCount[groupPosition];
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return group.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RelativeLayout layout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.group_create_forget, null);
		TextView groupTitleTextView = (TextView)layout.findViewById(R.id.groupTitle);
		String groupTitle = group[groupPosition];//context.getString(R.string.setting);
		groupTitleTextView.setText(groupTitle);
		return layout;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addForgetItem(String forgetItemName) {
		forgetItemArrayList.add(forgetItemName);
		notifyDataSetChanged();
	}
	
	public boolean saveDataToDatabase() {
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<Forget, Integer>forgetDao = databaseHelper.getForgetDao();
		Forget forget = new Forget();
		String remindName = remindNameEditText.getText().toString();
		Integer minute = remindTimePicker.getCurrentMinute();
		Integer hour = remindTimePicker.getCurrentMinute();
		forget.setName(remindName);
		forget.setMinute(minute);
		forget.setHour(hour);
		
		try {
			forgetDao.create(forget);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		Dao<ForgetItem, Integer>forgetItemDao = databaseHelper.getForgetItemDao();
		for (String itemName : forgetItemArrayList) {
			ForgetItem forgetItem = new ForgetItem();
			forgetItem.setName(itemName);
			forgetItem.setForgetId(forget);
			try {
				forgetItemDao.create(forgetItem);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	class CreateButtonListener implements OnClickListener {
		
		@Override
		public void onClick(View view) {
			Intent intent = new Intent();
			intent.setClass(context, CreateForgetItemActivity.class);
			//context.startActivity(intent);
			context.startActivityForResult(intent, 1);
		}
	}

}
