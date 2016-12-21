package com.example.omar.diabetestracerapp.data_model;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by OMAR on 12/9/2016.
 */
public class Meal {
	
	public final static String _Meal_TABLE="Meal";
	
	// Meal Columns names
	public final static String _ID = "id";
	public final static String _TYPE = "type";
	public final static String _DESCRIPTION = "description";
	public final static String _IMAGE = "image";
	public final static String _DATE_TIME = "date_time";
	public final static String _USERS_ID = "Users_id";

	public static String[] MEAL_COLS = {Meal._ID,
			Meal._TYPE,
			Meal._DESCRIPTION,
			Meal._IMAGE,
			Meal._DATE_TIME,
			Meal._USERS_ID};
	
	private int id;
	private String type;
	private String description;
	private String image;
	private Date date_time;
	private int users_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getUsers_id() {
		return users_id;
	}
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	
	/**
	 * Method used to validate a complete Meal object.  
	 * 
	 * @return
	 */
	public Boolean validate() {
		Boolean result = false;
		if (this.type != null && !this.type.equals("") && 
				this.description != null && !this.description.equals("") && 
				this.image != null && !this.image.equals("") && 
				this.date_time!=null) {
			result = true;
		}
		return result;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public ContentValues getContentValuesObject(){
		ContentValues userFieldValues = new ContentValues();
		userFieldValues.put(Meal._ID, this.getId());
		userFieldValues.put(Meal._TYPE, this.getType());
		userFieldValues.put(Meal._DESCRIPTION, this.getDescription());
		userFieldValues.put(Meal._DATE_TIME, this.getDate_time().toString());
		userFieldValues.put(Meal._IMAGE, this.getImage());
		userFieldValues.put(Meal._USERS_ID, this.getUsers_id());
		return userFieldValues;
	}

}
