package com.spmno.donotforget.data;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

public class ForgetItem implements Serializable {
	@DatabaseField(id=true) 
	private String forgetItemNo;
	@DatabaseField
	private String name;
	@DatabaseField
	private String forgetId;
}
