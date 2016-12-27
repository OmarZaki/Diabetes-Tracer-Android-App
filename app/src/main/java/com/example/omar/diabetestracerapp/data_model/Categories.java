package com.example.omar.diabetestracerapp.data_model;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by OMAR on 12/9/2016.
 */
public class Categories {

	public final static String _Categories_TABLE="Categories";
	
	// Categories Columns names
	public final static String _ID = "id";
	public final static String _DATE_TIME = "date_time";
	public final static String _VALUE = "value";
	public final static String _USERS_ID = "users_id";
	public final static String _CATEGORY_NAME_ID = "Category_name_id";
	
	private int id;
	private Date date_time;
	private String value;
	private int Users_id;
	private int Category_name_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getUsers_id() {
		return Users_id;
	}
	public void setUsers_id(int users_id) {
		Users_id = users_id;
	}
	public int getCategory_name_id() {
		return Category_name_id;
	}
	public void setCategory_name_id(int category_name_id) {
		Category_name_id = category_name_id;
	}
	
	public boolean validate(){
		if(this.getCategory_name_id()>=0 && this.getCategory_name_id()<4
				&& this.getUsers_id()>0 && this.getValue()!=null)
			return true;
		return false;
	}

	public static JSONObject toJSONObject(Categories categories) throws JSONException {
		Gson gson =new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		return new JSONObject(gson.toJson(categories));
	}

	public ContentValues getContentValuesObject(){
		ContentValues userFieldValues = new ContentValues();
		userFieldValues.put(Categories._ID, this.getId());
		userFieldValues.put(Categories._VALUE, this.getValue());
		userFieldValues.put(Categories._CATEGORY_NAME_ID, this.getCategory_name_id());
		userFieldValues.put(Categories._DATE_TIME, this.getDate_time().toString());
		userFieldValues.put(Categories._USERS_ID, this.getUsers_id());
		return userFieldValues;
	}

    public static ArrayList<Categories> convertJsonToList(String categories) {
        Gson gson =new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        ArrayList<Categories> categoriesAsString = gson.fromJson(categories, new TypeToken<ArrayList<Categories>>(){}.getType());
        return categoriesAsString;
    }

}
