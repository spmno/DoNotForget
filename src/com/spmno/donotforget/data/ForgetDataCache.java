package com.spmno.donotforget.data;

import java.util.ArrayList;

public class ForgetDataCache {
	private ArrayList<String> forgetItemList;
	
	private ForgetDataCache(){}
	
	static class SingletonHolder {
		static ForgetDataCache instance = new ForgetDataCache();
	}
	
	public static ForgetDataCache getInstance() {
		return SingletonHolder.instance;
	}
	
	public void addForgetItem(String itemName) {
		forgetItemList.add(itemName);
	}
}
