package com.spmno.donotforget.log;

public class ForgetLog {
	
	public void saveLog(String log) {
		SdcardLogger.getInstance().saveLog(log);
	}
}
