package com.tobeagile.training.ebaby.services;

public final class OffHoursMock implements Hours {

	private boolean isOffHours; 
	
	private OffHoursMock() {
	}
	public static OffHoursMock getInstance() {
		return new OffHoursMock();
	}
	
	public boolean isOffHours() {
		return this.isOffHours;
	}
	
	public void setOffHours(boolean value) {
		this.isOffHours = value;
	}
}
