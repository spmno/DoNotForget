package com.spmno.donotforget.data;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

public class ForgetItem implements Serializable {
	@DatabaseField(generatedId=true) 
	private String forgetItemNo;
	@DatabaseField(canBeNull=false)
	private String name;
	@DatabaseField(foreign=true)
	private Forget forget;
}
