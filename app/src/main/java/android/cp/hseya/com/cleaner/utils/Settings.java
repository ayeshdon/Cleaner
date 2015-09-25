package android.cp.hseya.com.cleaner.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ayesh on 5/19/2015.
 */
public class Settings {

    private final String PREF_NAME = "android.clky2.hseya.com.clky2";
    private Context context;


    public Settings(Context context) {

        this.context = context;

    }

    /*
     * get shared Preference of application
     */
    public SharedPreferences getPref() {
        return context.getSharedPreferences(PREF_NAME, 0);
    }

    /*
     * get application login user name
     */
    public String getUserName() {
        return getPref().getString("user_name", "");
    }

    /*
     * set application login user email
	 */
    public void setUserName(String value) {
        getPref().edit().putString("user_name", value).commit();
    }
    /*
     * get application login user email
     */
    public String getUserEmail() {
        return getPref().getString("user_email", "");
    }

    /*
     * set application login user name
	 */
    public void setUserEmail(String value) {
        getPref().edit().putString("user_email", value).commit();
    }


    /*
     * get application login user id
     */
    public String getUserID() {
        return getPref().getString("user_id", "");
    }

    /*
     * set application login user id
	 */
    public void setUserID(String value) {
        getPref().edit().putString("user_id", value).commit();
    }

    /*
     * get application login flag
     */
    public boolean getLoginAlready() {
        return getPref().getBoolean("is_login", false);
    }

    /*
	 * set application login flag
	 */
    public void setLoginAlready(boolean value) {
        getPref().edit().putBoolean("is_login", value).commit();
    }
}
