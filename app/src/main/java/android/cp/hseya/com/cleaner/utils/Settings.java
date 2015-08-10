package android.cp.hseya.com.cleaner.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ayesh on 5/19/2015.
 */
public class Settings {

    private final String PREF_NAME = "android.clky2.hseya.com.clky2";
    private Context context;


    public Settings(Context context){

        this.context=context;

    }

    /*
     * get shared Preference of application
     */
    public SharedPreferences getPref() {
        return context.getSharedPreferences(PREF_NAME, 0);
    }


    /*
	 * set application login user name
	 */
    public void setUserName(String value) {
        getPref().edit().putString("user_name", value).commit();
    }

    /*
     * get application login user name
     */
    public String getUserName() {
        return  getPref().getString("user_name", "");
    }
    /*
	 * set application login flag
	 */
    public void setLoginAlready(boolean value) {
        getPref().edit().putBoolean("is_login", value).commit();
    }

    /*
     * get application login flag
     */
    public boolean getLoginAlready() {
        return  getPref().getBoolean("is_login",false);
    }
}
