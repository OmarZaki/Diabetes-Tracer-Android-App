package com.example.omar.diabetestracerapp.shared_preference;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by OMAR on 12/20/2016.
 */

public class SharedPreferenceMethods {

    SharedPreferences sharedPreferences;

    public SharedPreferenceMethods(Activity activity) {
        this.sharedPreferences = activity.getSharedPreferences(SharedKeys.SHARED_PREFERENCE_FILE, activity.MODE_PRIVATE);
    }
    /** ---------> Store Methods <------------ **/
    /**
     * store the remember me indicator in the database .
     *
     * @param b
     */
    public void storeRememberMeIndicator(boolean b) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(SharedKeys.REMEMEBRE_ME_INDICATOR, b);
        editor.commit();
    }

    /**------------> Retrieve Methods <----------- */
    /**
     * get the remember me indicator from the shared preference
     *
     * @return return false if if the value is not exist.
     */
    public boolean retrieveRmemeberMeIndicator() {
        boolean rememberMeIndicator = sharedPreferences.getBoolean(SharedKeys.REMEMEBRE_ME_INDICATOR, false);
        return rememberMeIndicator;
    }

    public void storeHoursLeft(long hours) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putLong(SharedKeys.TODAY_HOURS_LEFT, hours);
        editor.commit();
    }

    public void storeMinutesLeft(long minutes) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putLong(SharedKeys.TODAY_MINUTES_LEFT, minutes);
        editor.commit();
    }

    public Long retrieveHoursLeft() {
        Long hoursLeft = sharedPreferences.getLong(SharedKeys.TODAY_HOURS_LEFT, -1);
        return hoursLeft;
    }

    public Long retrieveMinutesLeft() {
        Long MinutesLeft = sharedPreferences.getLong(SharedKeys.TODAY_MINUTES_LEFT, -1);
        return MinutesLeft;

    }


}
