package com.spmno.donotforget.data;

import java.io.Serializable;

import android.text.format.Time;

import com.j256.ormlite.field.DatabaseField;

public class Forget implements Serializable {
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false, useGetSet=true)
	private String name;
	@DatabaseField(useGetSet=true)
	private String place;
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	@DatabaseField(useGetSet=true)
	private Time time;
	
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
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
}
