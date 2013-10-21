package com.spmno.donotforget.adapter;

import java.util.ArrayList;

import com.spmno.donotforget.CreateForgetItemActivity;
import com.spmno.donotforget.R;

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
	public ForgetCreateListAdapter(Activity context) {
		this.context = context;
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
				LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_time, null);
				TextView remindTimeTitle = (TextView)linearLayout.findViewById(R.id.remindTextView);
				ImageView remindTimeIcon = (ImageView)linearLayout.findViewById(R.id.remindImageView);
				TimePicker remindTimer = (TimePicker)linearLayout.findViewById(R.id.remindTimePicker);
				remindTimeTitle.setText(context.getString(R.string.time));
				//linearLayout.addView(remindTimeTitle);
				//linearLayout.addView(remindTimeIcon);
				//linearLayout.addView(remindTimer);
				return linearLayout;
			} else if (childPosition == 1) {
				LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_place, null);
				TextView remindPlaceTitle = (TextView)linearLayout.findViewById(R.id.placeTitleTextView);
				ImageView remindPlaceIcon = (ImageView)linearLayout.findViewById(R.id.placeTitleImageView);
				EditText remindPlaceName = (EditText)linearLayout.findViewById(R.id.placeName);
				//linearLayout.addView(remindPlaceTitle);
				//linearLayout.addView(remindPlaceIcon);
				//linearLayout.addView(remindPlaceName);
				return linearLayout;
			} else if (childPosition == 2) {
				LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_event, null);
				TextView remindEventTitle = (TextView)linearLayout.findViewById(R.id.eventTitleTextView);
				Button businessButton = (Button)linearLayout.findViewById(R.id.businessButton);
				Button tripButton = (Button)linearLayout.findViewById(R.id.tripButton);
				Button otherButton = (Button)linearLayout.findViewById(R.id.otherButton);
				//linearLayout.addView(remindEventTitle);
				//linearLayout.addView(businessButton);
				//linearLayout.addView(tripButton);
				//linearLayout.addView(otherButton);
				return linearLayout;
			}
			
		} else if (groupPosition == 1) {
			if (childPosition == forgetItemArrayList.size()) {
				Button addForgetButton = new Button(context);
				String addText = context.getString(R.string.add_forget);
				addForgetButton.setText(addText);
				addForgetButton.setOnClickListener(new CreateButtonListener());
				return addForgetButton;
			} else {
				TextView forgetItemTextView = new TextView(context);
				String forgetItemName = forgetItemArrayList.get(childPosition);
				forgetItemTextView.setText(forgetItemName);
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
