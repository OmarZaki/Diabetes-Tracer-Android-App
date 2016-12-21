package com.example.omar.diabetestracerapp.data_model;

import android.content.ContentValues;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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


	public static ArrayList<Meal> convertJsonToList(String meals) {
		Gson gson =new GsonBuilder()
				.setDateFormat("yyyy-MM-dd hh:mm:ss.S")
				.create();
        return gson.fromJson(meals ,new TypeToken<ArrayList<Meal>>(){}.getType());
	}

    public static JSONObject toJSONObject(Meal meal) throws JSONException {
        Gson gson = new Gson();
        return new JSONObject(gson.toJson(meal));
    }

    public void encodeImageToBase64String() throws IOException {
        File f = new File(this.image);
        int size = (int) f.length();
        byte[] bytes = new byte[size];
        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(f));
        buf.read(bytes, 0, bytes.length);
        buf.close();
        this.image = Base64.encodeToString(bytes,Base64.NO_WRAP);
    }

}
