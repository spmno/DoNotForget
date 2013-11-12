package com.spmno.donotforgetsimple.data;

import java.util.ArrayList;

public class ForgetDataCache {
	private ArrayList<String> forgetItemList;
	private Forget currentForget;
	
	public ArrayList<String> getForgetItemList() {
		return forgetItemList;
	}

	public void setForgetItemList(ArrayList<String> forgetItemList) {
		this.forgetItemList = forgetItemList;
	}

	public Forget getCurrentForget() {
		return currentForget;
	}

	public void setCurrentForget(Forget currentForget) {
		this.currentForget = currentForget;
	}

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
