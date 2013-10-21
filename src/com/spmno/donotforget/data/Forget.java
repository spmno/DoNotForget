package com.spmno.donotforget.data;

import java.io.Serializable;

import android.text.format.Time;

import com.j256.ormlite.field.DatabaseField;

public class Forget implements Serializable {
	
	@DatabaseField(generatedId=true)
	private String forgetNo;
	@DatabaseField(canBeNull=false)
	private String name;
	@DatabaseField
	private Time time;
}
