package com.spmno.donotforgetsimple.data;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

public class ForgetItem  {
	@DatabaseField(generatedId=true, useGetSet=true) 
	private int id;
	@DatabaseField(canBeNull=false, useGetSet=true)
	private String name;
	@DatabaseField(foreignColumnName="id", foreign=true, foreignAutoRefresh = true, useGetSet=true)
	private Forget forgetId;
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
	public Forget getForgetId() {
		return forgetId;
	}
	public void setForgetId(Forget forgetId) {
		this.forgetId = forgetId;
	}
}
