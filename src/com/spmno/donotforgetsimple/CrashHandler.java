package com.spmno.donotforgetsimple;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

public class CrashHandler implements UncaughtExceptionHandler {

	private static CrashHandler instance;
	private CrashHandler() {}
	
	public synchronized static CrashHandler getInstance() {
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}
	
	public void init(Context context) {
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

}
