package android.cp.hseya.com.cleaner.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ayesh on 5/17/2015.
 */
public class Validator {


    /**
     * email address validation class
     */
    public static boolean isValidEmail(String email) {
        try {

            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            Pattern p = Pattern.compile(ePattern);
            Matcher m = p.matcher(email);
            return m.matches();

        } catch (Exception e) {
            return false;
        }
    }


}
