package com.example.omar.diabetestracerapp.data_model;

import java.sql.Date;

public class InsulinDose {

	public final static String _InsulinDose_TABLE="InsulinDose";
	
	// InsulinDose Column names
	public final static String _ID = "id";
	public final static String _QUANTITY = "quantity";
	public final static String _TAKEN = "taken";
	public final static String _DATE_TIME = "date_time";
	public final static String _USERS_ID = "Users_id";
	
	private int id;
	private float quantity;
	private boolean taken;
	private Date date_time;
	private int Users_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public boolean isTaken() {
		return taken;
	}
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	public int getUsers_id() {
		return Users_id;
	}
	public void setUsers_id(int users_id) {
		Users_id = users_id;
	}
	
	public boolean validate(){
		if(this.getQuantity()>0 &&
				this.getDate_time()!=null)
			return true;
		return false;
	}
	
}
