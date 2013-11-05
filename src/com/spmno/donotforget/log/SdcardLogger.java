package com.spmno.donotforget.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.widget.Toast;

public class SdcardLogger {
	
	FileOutputStream logOutputStream = null;
	
	static class SingletonHolder {
		static SdcardLogger instance = new SdcardLogger();
	}
	
	public static SdcardLogger getInstance() {
		return SingletonHolder.instance;
	}
	
	private SdcardLogger() {
		setUpEnvironment();
	}
	
	private void setUpEnvironment() {
		if (Environment.getExternalStorageState().equals((Environment.MEDIA_MOUNTED))){
            File sdCardDir=Environment.getExternalStorageDirectory();
            File saveFile=new File(sdCardDir, "forget_log"+".txt");
            try{
                saveFile.createNewFile();
            }
            catch(IOException e1){                
                //Toast.makeText(getApplicationContext(), e1.getMessage(),Toast.LENGTH_SHORT).show();
            }
            try{
            	logOutputStream = new FileOutputStream(saveFile);
            }
            catch(FileNotFoundException e){
                //Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
	}
	
	public void saveLog(String content) {
		if (logOutputStream != null) {
			try {
				logOutputStream.write(content.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
