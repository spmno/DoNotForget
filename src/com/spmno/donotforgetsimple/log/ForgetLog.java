package com.spmno.donotforgetsimple.log;

public class ForgetLog {
	
	public void saveLog(String log) {
		SdcardLogger.getInstance().saveLog(log);
	}
}
