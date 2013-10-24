package com.spmno.donotforget.data;

import com.j256.ormlite.field.DatabaseField;

public class Forget {
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(canBeNull=false, useGetSet=true)
	private String name;
	@DatabaseField(useGetSet=true)
	private String place;

	@DatabaseField(useGetSet=true)
	private int hour;
	@DatabaseField(useGetSet=true)
	private int minute;
	
	
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
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
}
