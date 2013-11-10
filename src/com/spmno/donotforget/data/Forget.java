package com.spmno.donotforget.data;

import com.j256.ormlite.field.DatabaseField;

public class Forget implements Cloneable {
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false, useGetSet=true)
	private String name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object clone() {
		Forget forget = null;  
        try{  
        	forget = (Forget)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return forget; 
	}
}
