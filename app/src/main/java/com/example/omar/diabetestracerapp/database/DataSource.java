package com.example.omar.diabetestracerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.omar.diabetestracerapp.auxiliary.TypeEvent;
import com.example.omar.diabetestracerapp.data_model.Categories;
import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.data_model.Meal;
import com.example.omar.diabetestracerapp.data_model.Messages;
import com.example.omar.diabetestracerapp.data_model.Schedule;
import com.example.omar.diabetestracerapp.data_model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by OMAR on 12/14/2016.
 */

public class DataSource {

    // database fields
    private SQLiteDatabase database;
    private SqlHelper dbHelper;

    /**
     * Constructor
     *
     * @param context
     */
    public DataSource(Context context) {
        dbHelper = new SqlHelper(context);

    }

    /**
     * Open the database
     */
    public void open() {
        this.database = dbHelper.getWritableDatabase();
    }

    /**
     * Close database
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Insert the User object into the database
     *
     * @param user
     */
    public void insertUserToDataBase(User user) {
        open();
        ContentValues contentValues = user.getContentValuesObject();
        long i = database.insert(User._USER_TABLE, null, contentValues);
        if (i != 0) {
            Log.i("TAG", "User's record inserted!");

        } else {
            Log.i("TAG", "User's record is not inserted !");
        }
        close();
    }

    /**
     * Clean all User table records
     *
     * @param
     */
    public void cleanTables() {
        open();
        long i = database.delete(User._USER_TABLE, null, null);
        long i2 = database.delete(InsulinDose._InsulinDose_TABLE, null, null);
        close();
    }

    public void cleanTable(String tableName) {
        open();
        long i = database.delete(tableName, null, null);
        close();
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(SqlHelper.DATABASE_NAME);
        Log.i("DATABASE", "Database has been erased !");
    }

    /**
     * Insert the list of insulin dose from database;
     */
    public void insertSchedule(Schedule schedule) {


        ContentValues scheduleContentValues = schedule.getContentValuesObject();
        long i = database.insert(Schedule._SCHEDULE_TABLE, null, scheduleContentValues);
        if (i != 0) {
            Log.i("TAG", "User's record inserted!");
        } else {
            Log.i("TAG", "User's record is not inserted !");
        }


    }

    /**
     * Insert the list of insulin dose from database;
     *
     * @param insulinDoses
     */
    public void insertListOfInsulinDoses(List<InsulinDose> insulinDoses) {
        open();
        Log.i("INSULINDOSE-INSERT", "Inserted");
        for (InsulinDose insulin : insulinDoses) {
            ContentValues insulinContentValues = insulin.getContentValuesObject();
            long i = database.insert(InsulinDose._InsulinDose_TABLE, null, insulinContentValues);
            if (i != 0) {
                Log.i("TAG", "User's record inserted!");

                // Insert into schedule table.
                Schedule schedule = new Schedule();
                schedule.setDate(insulin.getDate_time());
                schedule.setTitle("Insulin Dose");
                schedule.setType(TypeEvent.DOSE);
                insertSchedule(schedule);

            } else {
                Log.i("TAG", "User's record is not inserted !");
            }
        }
        close();
    }

    /**
     * Update the current insulin dose;
     *
     * @param insulinDose
     */
    public void updateInsulinDoseRecord(InsulinDose insulinDose) {
        open();
        ContentValues cv = insulinDose.getContentValuesObject();
        long i = database.update(InsulinDose._InsulinDose_TABLE, cv, InsulinDose._ID + "=" + insulinDose.getId(), null);
        if (i != 0) {
            Log.i("TAG", "Insulin's record is inserted!");

        } else {
            Log.i("TAG", "Insulin's record is not inserted !");
        }
        open();
    }

    /**
     * get current insulin dose ;
     *
     * @param date
     * @return
     */
    public InsulinDose retrieveCurrentInsulinDose(Date date) {

        //TODO 1. Get All records from dataBase;
        //TODO 2. Extract the current date record ;

        open();
        InsulinDose insulinDose = null;
        Cursor cursor = database.query(InsulinDose._InsulinDose_TABLE, InsulinDose._INSULIN_COLS, null, null, null, null, null);
        cursor.moveToFirst();
        Log.i("DATASOURCE-INSULINDOSE", "Get insulin dose");
        while (cursor.moveToNext()) {
            insulinDose = InsulinDose.getInsulinDoseObject(cursor);
            String currentDate = InsulinDose.getDateFromDateTimeObejct(date);
            String insulinDoseDate = InsulinDose.getDateFromDateTimeObejct(insulinDose.getDate_time());
            if (currentDate.equals(insulinDoseDate)) {
                return insulinDose;
            }
        }
        cursor.close();
        close();
        return insulinDose;
    }

    /**
     * Insert the Meal object into the database
     *
     * @param meal
     */
    public void insertMealToDataBase(Meal meal) {
        open();
        ContentValues contentValues = meal.getContentValuesObject();
        long i = database.insert(Meal._Meal_TABLE, null, contentValues);
        if (i != 0) {
            Log.i("TAG", "Meal's record inserted!");
        } else {
            Log.i("TAG", "Meal's record is not inserted !");
        }
        close();
    }

    /**
     * Insert the Meal object into the database
     *
     * @param categories
     */
    public void insertCategoriesToDataBase(Categories categories) {
        open();
        ContentValues contentValues = categories.getContentValuesObject();
        long i = database.insert(Categories._Categories_TABLE, null, contentValues);
        if (i != 0) {
            Log.i("TAG", "Entry inserted!");

        } else {
            Log.i("TAG", "Entry is not inserted !");
        }
        close();
    }

    /**
     * Insert the list of meals from database;
     *
     * @param meals
     */
    public void insertListOfMeals(List<Meal> meals) {
        open();
        for (Meal meal : meals) {
            ContentValues mealContentValues = meal.getContentValuesObject();
            long i = database.insert(Meal._Meal_TABLE, null, mealContentValues);
            if (i != 0) {
                Log.i("TAG", "Meals record inserted!");
                // Insert into schedule table.
                Schedule schedule = new Schedule();
                schedule.setDate(meal.getDate_time());
                schedule.setTitle("Meal");
                schedule.setType(TypeEvent.MEAL);
                insertSchedule(schedule);
            } else {
                Log.i("TAG", "Meals record is not inserted !");
            }
        }
        close();
    }

    /**
     * Insert the list of categories from database;
     *
     * @param categoriesList
     */
    public void insertListOfCategories(List<Categories> categoriesList) {
        open();
        for (Categories categories : categoriesList) {
            ContentValues categoriesContentValues = categories.getContentValuesObject();
            long i = database.insert(Categories._Categories_TABLE, null, categoriesContentValues);
            if (i != 0) {
                Log.i("TAG", "Meals record inserted!");
                // Insert into schedule table.
                Schedule schedule = new Schedule();
                schedule.setDate(categories.getDate_time());
                if(categories.getCategory_name_id() != 1){
                    schedule.setTitle("Heart Rate");
                    schedule.setType(TypeEvent.HEARATE);
                }
                if(categories.getCategory_name_id() != 2){
                    schedule.setTitle("Medication");
                    schedule.setType(TypeEvent.MEDITCATION);
                }
                if(categories.getCategory_name_id()!=3){
                    schedule.setTitle("Blood Sugar");
                    schedule.setType(TypeEvent.BLOODSUGAR);
                }

                insertSchedule(schedule);
            } else {
                Log.i("TAG", "Meals record is not inserted !");
            }
        }
        close();
    }

    /**
     * Insert one Message object.
     *
     * @param messages
     */
    public void insertMessage(Messages messages) {
        open();
        ContentValues messagesContentValuesObject = messages.getContentValuesObject();
        long i = database.insert(Messages._MESSAGES_TABLE, null, messagesContentValuesObject);
        if (i != 0) {
            Log.i("DATASOURCE", "Message Object has been inserted to local database");
        } else {
            Log.i("DATASOURCE-ERROR", "Message object insertion failed !");

        }
        close();
    }

    /**
     * Insert a list of messages.
     *
     * @param messagesList
     */
    public void insertListOfMessages(List<Messages> messagesList) {
        open();
        for (Messages msg : messagesList) {
            ContentValues messagesContentValuesObject = msg.getContentValuesObject();
            long i = database.insert(Messages._MESSAGES_TABLE, null, messagesContentValuesObject);
            if (i != 0) {
                Log.i("DATASOURCE", "Message Object has been inserted to local database");
                // Insert into schedule table.
                Schedule schedule = new Schedule();
                schedule.setDate(msg.getDate_time());
                schedule.setTitle("Message");
                schedule.setType(TypeEvent.MESSAGE);
                insertSchedule(schedule);

            } else {
                Log.i("DATASOURCE-ERROR", "Message object insertion failed !");

            }
        }
        close();
    }

    /**
     * Retrieve all messages records
     *
     * @return
     */
    public List<Messages> retrieveListMessages() {
        List<Messages> messagesList = null;

        open();
        Cursor cursor = database.query(Messages._MESSAGES_TABLE, Messages._MESSAGES_COLS, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            messagesList = new ArrayList<Messages>();
            Messages messages = Messages.getMessageObjectFromCursor(cursor);
            messagesList.add(messages);
        }
        cursor.close();
        close();
        return messagesList;
    }


    public ArrayList<Schedule> retrieveListEvents(int month) {
        open();

        ArrayList<Schedule> scheduleList = null;
        //String sql = "SELECT * FROM " + Schedule._SCHEDULE_TABLE+" WHERE MONTH("+Schedule._COL_DATE+")=? ; ";
        String sql = "SELECT * FROM " + Schedule._SCHEDULE_TABLE+" ;";
        Cursor cursor = database.query(Schedule._SCHEDULE_TABLE,Schedule._SCHEDULE_COLL,null,null,null,null,null);
        cursor.moveToFirst();
        if(cursor.moveToNext()){
            Schedule s = Schedule.getScheduleDoseObject(cursor);
            // check if the date is
            Calendar cal = Calendar.getInstance();
            cal.setTime(s.getDate());
            int itemMonth = cal.get(Calendar.MONTH);
            if(itemMonth != month){
                scheduleList.add(s);
            }
        }
        close();
        return scheduleList;
    }

    private List<Categories> retrieveListCategories() {
        open();
        List<Categories> CatList = null;
        Cursor cursor = database.query(Categories._Categories_TABLE, Categories._CATEGORRIES_COLL, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            CatList = new ArrayList<Categories>();
            Categories cat = Categories.getCategoriesDoseObject(cursor);
            CatList.add(cat);
        }
        cursor.close();
        close();
        return CatList;


    }

    private List<Meal> retrieveListMeal() {
        List<Meal> MealList = null;

        open();
        Cursor cursor = database.query(Meal._Meal_TABLE, Meal.MEAL_COLS, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            MealList = new ArrayList<Meal>();
            Meal meal = Meal.getInsulinDoseObject(cursor);
            MealList.add(meal);
        }
        cursor.close();
        close();
        return MealList;
    }

    private List<InsulinDose> retrieveListDoses() {
        List<InsulinDose> doseList = null;

        open();
        Cursor cursor = database.query(InsulinDose._InsulinDose_TABLE, InsulinDose._INSULIN_COLS, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            doseList = new ArrayList<InsulinDose>();
            InsulinDose dose = InsulinDose.getInsulinDoseObject(cursor);
            doseList.add(dose);
        }
        cursor.close();
        close();
        return doseList;
    }

    public List<Schedule> retrieveALlEventByDate() {
        // TODO 1. select the records ordered by date.
        return new ArrayList<Schedule>();
    }

    /**
     * get the User from database
     *
     * @return
     */
    public User retrieveUserFromDataBase() {
        open();
        User user = null;
        Cursor cursor = database.query(User._USER_TABLE, User.USER_COLS, null, null, null, null, null);
        cursor.moveToFirst();
        Log.i("TTT", String.valueOf(cursor.getCount()));
        if (cursor.getCount() != 0) {
            user = User.getUserFromCourser(cursor);
        }
        cursor.close();
        close();
        return user;
    }
}
