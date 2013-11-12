package com.spmno.donotforgetsimple.adapter;

import java.sql.SQLException;
import java.util.ArrayList;

import com.j256.ormlite.dao.Dao;
import com.spmno.donotforgetsimple.CreateForgetItemActivity;
import com.spmno.donotforgetsimple.R;
import com.spmno.donotforgetsimple.data.DataBaseHelper;
import com.spmno.donotforgetsimple.data.Forget;
import com.spmno.donotforgetsimple.data.ForgetItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForgetCreateListAdapter extends BaseExpandableListAdapter {

	private String[] group = {"…Ë÷√", "ŒÔ∆∑List" };
	private ArrayList<String> forgetItemArrayList;
	private Activity context;
	
	// useful control
	EditText remindNameEditText;
	Button addForgetButton;
	String remindName = "";	
	
	final int TYPE_LENGTH = 5;
	final int NAME_TYPE = 0;
	final int TIME_TYPE = 1;
	final int PLACE_TYPE = 2;
	final int BUTTON_TYPE = 3;
	final int TEXT_TYPE = 4;
	
	public ForgetCreateListAdapter(Activity context) {
		this.context = context;
		forgetItemArrayList = new ArrayList<String>();
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
	public int getChildTypeCount() {
		return TYPE_LENGTH;
	}

	@Override
	public int getChildType(int groupPosition, int childPosition) {
		if (groupPosition == 0) {
			return childPosition;
		} else {
			if (childPosition == forgetItemArrayList.size()) {
				return BUTTON_TYPE;
			} else {
				return TEXT_TYPE;
			}
		}
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (groupPosition == 0) {
			if (convertView == null) {
				convertView = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_event, null);
				//TextView remindEventTitle = (TextView)linearLayout.findViewById(R.id.eventTitleTextView);
				if (remindNameEditText == null) {
					remindNameEditText = (EditText)convertView.findViewById(R.id.forgetNameTextView);
				}
			}
			return convertView;
		} else if (groupPosition == 1) {
			if (childPosition == forgetItemArrayList.size()) {
				if (convertView == null) {
					convertView = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.child_create_forget_add, null);
					addForgetButton = (Button)convertView.findViewById(R.id.addForgetItemButton);
					addForgetButton.setOnClickListener(new CreateButtonListener());
				}
				
				return convertView;
			} else {
				TextView forgetItemTextView = new TextView(context);
				String forgetItemName = forgetItemArrayList.get(childPosition);
				forgetItemTextView.setText(forgetItemName);
				forgetItemTextView.setTextColor(Color.rgb(255, 255, 255));
				return forgetItemTextView;
			}

		}
		
		
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		//return childCount[groupPosition];
		if (groupPosition == 0) {
			return 1;
		} else {
			return (forgetItemArrayList.size() + 1);
		}
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
		if (convertView != null) {
			return convertView;
		}
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
		forget.setName(remindName);
		
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
	
	public void saveControlData() {
		remindName = remindNameEditText.getText().toString();
	}
	
	public void recoverControlData() {
		if (remindNameEditText != null) {
			remindNameEditText.setText(remindName);
		}
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
